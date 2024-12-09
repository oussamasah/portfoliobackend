package dev.portfolio.oussama.dto.ServiceInfo;

import lombok.Data;

@Data
    public class ServiceInfoResponse {
        private String title;
        private String message;
        private String error;

        public ServiceInfoResponse(String title,boolean success) {
            this.title = title;
            if(success){
                this.message = "Title saved successfuly";

            }else{
                this.error =title;

            }
        }

    public ServiceInfoResponse(String title) {
        this.title = title;

    }

}
