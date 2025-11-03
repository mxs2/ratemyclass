package com.ratemyclass.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome não pode ter mais que 100 caracteres")
    private String firstName;

    @NotBlank(message = "O sobrenome é obrigatório")
    @Size(max = 100, message = "O sobrenome não pode ter mais que 100 caracteres")
    private String lastName;

    @Email(message = "Formato de e-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    @Size(max = 150, message = "O e-mail não pode ter mais que 150 caracteres")
    private String email;

    @NotBlank(message = "O número de matrícula é obrigatório")
    @Size(max = 20, message = "A matrícula não pode ter mais que 20 caracteres")
    private String studentId;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
    private String password;

    @Size(max = 100, message = "O curso não pode ter mais que 100 caracteres")
    private String major;

    private Integer graduationYear;

    // Construtores
    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String firstName, String lastName, String email, String studentId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentId = studentId;
        this.password = password;
    }

    // Getters e Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }
}
