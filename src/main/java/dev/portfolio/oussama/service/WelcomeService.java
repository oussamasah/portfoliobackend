package dev.portfolio.oussama.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.portfolio.oussama.model.Skills;
import dev.portfolio.oussama.model.Welcome;
import dev.portfolio.oussama.repository.SkillsRepository;
import dev.portfolio.oussama.repository.WelcomeRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WelcomeService {
    private final WelcomeRepository welcomeRepository;
    private StanfordCoreNLP pipelineToUse;
    private  static Set<String> skillSynonyms;
    private static Set<String> allPossibleSkills;
    private final LevenshteinDistance distance = new LevenshteinDistance();
    private final  SkillsRepository ai_repo;
    public WelcomeService(WelcomeRepository welcomeRepository , SkillsRepository AIrepo)  {
        this.welcomeRepository = welcomeRepository;
        this.ai_repo = AIrepo;
    String profilePath = Paths.get("src\\main\\java\\dev\\portfolio\\oussama\\util\\profiles").toAbsolutePath().toString();

        // Set up NLP pipelines (English and French)
        Properties englishProps = new Properties();
        englishProps.setProperty("annotators", "tokenize,ssplit");
        englishProps.setProperty("tokenize.language", "en");
        englishProps.setProperty("ner.model", "edu/stanford/nlp/models/ner/english.muc.7class.caseless.distsim.crf.ser.gz");
        this.pipelineToUse = new StanfordCoreNLP(englishProps);



        // Load possible skills and skill synonyms from files

    }





    public Map<String, Object> extractSkills(String jobDescription) {
        this.allPossibleSkills = this.ai_repo.findAll()
                .stream()
                .map(Skills::getSkill) // Extract the `skill` field
                .collect(Collectors.toSet()); // Collect into a List
        this.skillSynonyms = this.ai_repo.findByActive(true)
                .stream()
                .map(Skills::getSkill) // Extract the `skill` field
                .collect(Collectors.toSet());



        String[] words = tokenizeJobDescription(jobDescription);


        List<String> extractedSkills = extractSkillsFromWords(words);
        Set<String> matchedSkills = getMatchedSkills(extractedSkills);
        String compatibilityScore = calculateCompatibilityScore(extractedSkills);


        Map<String, Object> result = new HashMap<>();
        result.put("availableSkills", extractedSkills);
        result.put("matchedSkills", matchedSkills);
        result.put("compatibilityPercentage", compatibilityScore);

        return result;
    }


    /*private String[] tokenizeJobDescription(String jobDescription) {
        // Convert to lowercase (optional, depending on use case)
        jobDescription = jobDescription.toLowerCase();

        // Define a regex to match word-like sequences (including alphanumeric and special characters like hyphens or slashes)
        String regex = "[a-zA-Z0-9/\\-]+";

        // Use regular expression to match words
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobDescription);

        // Collect all matched words into a list
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        // Convert the list to an array and return it
        return words.toArray(new String[0]);
    }*/
    private String[] tokenizeJobDescription(String jobDescription) {
        // Convert to lowercase (optional, depending on use case)
        jobDescription = jobDescription.toLowerCase();

        // Retrieve multi-word special terms from the repository
        List<String> specialTerms = this.ai_repo.findByMultiword(true)
                .stream()
                .map(Skills::getSkill) // Extract the `skill` field
                .collect(Collectors.toList());

        // Define a regex to match word-like sequences (including alphanumeric and special characters like hyphens or slashes)
        String regex = "[a-zA-Z0-9/\\-]+";

        // Use regular expression to match words
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jobDescription);

        // Collect all matched words into a list
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }

        // Now, check for multi-word special terms (skills) in the tokenized list
        List<String> finalTokens = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            // Always add the current word as a token
            String word = words.get(i).toLowerCase();
            String nextWord = (i + 1 < words.size()) ? words.get(i + 1).toLowerCase() : "";



            // Check if the combined phrase matches any special term using fuzzy matching
            boolean matched = false;
            if(word.length()>3 && nextWord.length()>3) {
                for (String term : specialTerms) {
                    if (term.toLowerCase().contains(word) && term.toLowerCase().contains(nextWord)) {
                        finalTokens.add(term); // Add the matched multi-word term as a single token
                        i++; // Skip the next word because it's already included in the match
                        matched = true;
                        break;
                    }
                }
            }

            // If no match, add the current word as a token
            if (!matched) {
                finalTokens.add(word);
            }
        }

        // Convert the list to an array and return it
        return finalTokens.toArray(new String[0]);
    }
/*    public String[] tokenizeJobDescription(String jobDescription) {
        // Validate input to prevent null pointer exceptions
        if (jobDescription == null || jobDescription.isBlank()) {
            return new String[0];
        }

        // Detect language and select appropriate pipeline
        String detectedLang = detectLanguage(jobDescription);
        StanfordCoreNLP pipelineToUse = "fr".equals(detectedLang) ? frenchPipeline : englishPipeline;

        // List of multi-word and single-word terms
        List<String> specialTerms = this.ai_repo.findByMultiword(true)
                .stream()
                .map(Skills::getSkill) // Extract the `skill` field
                .collect(Collectors.toList());

        // Pre-process input to unify special terms
        String processedDescription = jobDescription.toLowerCase();
        for (String term : specialTerms) {
            String unifiedToken = term.replaceAll(" ", "_"); // Replace spaces with underscores
            processedDescription = processedDescription.replaceAll("\\b" + term + "\\b", unifiedToken);
        }

        // Create and annotate CoreDocument with the selected pipeline
        CoreDocument document = new CoreDocument(processedDescription);
        pipelineToUse.annotate(document);

        // Extract tokens from the annotated document
        List<String> tokens = new ArrayList<>();
        for (CoreLabel token : document.tokens()) {
            String word = token.word();
            // Optionally, restore underscores to spaces for multi-word terms
            if (word.contains("_")) {
                tokens.add(word);
            } else {
                tokens.add(word);
            }
        }

        // Convert the list to an array and return
        return tokens.toArray(new String[0]);
    }
*/

    private List<String> extractSkillsFromWords(String[] words) {
        Set<String> matchedSkills = new HashSet<>();

        for (String word : words) {
            String normalizedWord = word.toLowerCase();
            System.out.println("word : "+words.toString());

            for (String possibleSkill : allPossibleSkills) {
                if (this.matchesWithFuzzy(possibleSkill,normalizedWord)) {
                    matchedSkills.add(possibleSkill);
                }
            }
        }
        return new ArrayList<>(matchedSkills);
    }


    private boolean matchesWithFuzzy(String skill, String word) {
        // Convert both inputs to lowercase before comparison
        skill = skill.toLowerCase();
        word = word.toLowerCase();
        System.out.println(word);
        // Determine the length and threshold
        int len = Math.max(skill.length(), word.length());
        int threshold = len <= 7   ?0 : len <= 10 ? 1: 2;

        // Apply the distance calculation with the modified threshold
        return distance.apply(skill, word) <= threshold;
    }

    public String calculateCompatibilityScore(List<String> extractedSkills) {
        if (extractedSkills.isEmpty()) {
            return "0.0";
        }

        // Assuming skillSynonyms is a Set<String> representing your skills
        Set<String> mySkillsSet = skillSynonyms; // your skills set

        // Find the fuzzy matches between mySkillsSet and extractedSkills
        Set<String> matchedSkills = extractedSkills.stream()
                .filter(skill -> mySkillsSet.stream().anyMatch(s->this.matchesWithFuzzy(s,skill)))
                .collect(Collectors.toSet());

        System.out.println("extractedSkills----------" + extractedSkills.size() + "---" + extractedSkills);
        System.out.println("matchedSkills----------" + matchedSkills.size() + "---" + matchedSkills);

        // Calculate the percentage of matched skills from your skills (not extractedSkills)
        double matchRatio = (double) matchedSkills.size() / extractedSkills.size();

        System.out.println("matchRatio----------" + matchRatio);
        return String.format("%.2f", Math.min(matchRatio * 100, 100));
        // Ensure the result is between 0 and 100

    }




    public Set<String> getMatchedSkills(List<String> extractedSkills) {
        // Assuming skillSynonyms is a Set<String>
        Set<String> mySkillsSet = skillSynonyms;
        return extractedSkills.stream()
                .filter(skill -> mySkillsSet.stream().anyMatch(s -> this.matchesWithFuzzy(s,skill)))

                .collect(Collectors.toSet());
    }


    public Optional<Welcome> get() {
        return welcomeRepository.findFirstByOrderByIdAsc();
    }

    public Optional<Welcome> save(String title, String tags, String description, String image, String cv) {
        Optional<Welcome> welcome = welcomeRepository.findFirstByOrderByIdAsc();
        Welcome entity = welcome.orElseGet(Welcome::new);

        entity.setTitle(title);
        entity.setTags(tags);
        entity.setDescription(description);
        if (image != null && !image.isEmpty()) {
            entity.setImage(image);
        }
        if (cv != null && !cv.isEmpty()) {
            entity.setCv(cv);
        }

        Welcome savedEntity = welcomeRepository.save(entity);
        return Optional.of(savedEntity);
    }
}
