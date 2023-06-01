package com.klinik.response.report.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Patient {

    private String surname;
    private String name;
    private String full_name;
    private Boolean gender;
    private String phone;
    private String address; 

    public Patient(){
        
    }

    public Patient(  String surname, String name, String full_name, Boolean gender ,String phone, String address ){
        this.surname = surname;
        this.name = name;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append("     6.1. Фамилия: ").append(surname).append(",\n")  
                      .append("     6.2. Имя: ").append(name).append(",\n")
                      .append("     6.3. Отчество: ").append(full_name).append(",\n")
                      .append("     6.4. Пол: ").append(gender == true ? "Муж" : "Жен").append(",\n")
                      .append("     6.5. Номер телефона: ").append(phone).append(",\n")
                      .append("     6.6. Адрес: ").append(address).append("\n }\n")
                      .toString();
    }
    
}
