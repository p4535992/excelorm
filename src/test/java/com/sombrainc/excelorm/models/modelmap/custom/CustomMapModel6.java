package com.sombrainc.excelorm.models.modelmap.custom;

import com.sombrainc.excelorm.annotation.Cell;
import com.sombrainc.excelorm.annotation.CellMap;
import lombok.Data;

import java.util.Map;

@Data
public class CustomMapModel6 {
    @CellMap(keyCell = "B67:H67", step = 3)
    private Map<Integer, User> map;

    @Data
    public static class User {
        @Cell(value = "B68")
        private String firstName;
        @Cell(value = "B69")
        private String lastName;
        @Cell(value = "B70")
        private Integer age;
        @Cell(value = "B71")
        private Gender gender;

        public User() {
        }

        public User(String firstName, String lastName, Integer age, Gender gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
        }
    }

}
