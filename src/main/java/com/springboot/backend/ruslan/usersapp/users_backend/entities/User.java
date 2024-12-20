package com.springboot.backend.ruslan.usersapp.users_backend.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Aqui se define la entidad User, que se mapea a la tabla users en la base de datos.
 * La clase User tiene los atributos id, name, lastname, email, username y password.
 * La clase User tiene los métodos getter y setter para cada atributo. 
 */

@Entity //Indica que la clase es una entidad de la base de datos
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica que el valor de la clave primaria se generará automáticamente
    private Long id;
    @NotEmpty
    private String lastname;

    @NotBlank //Indica que el campo no puede estar vacío ni ser nulo ni contener solo espacios en blanco
    private String name;
    @NotBlank
    private String birthday;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String country;
    @NotEmpty
    private String municipality;
    @NotEmpty
    private String province;
    @NotNull
    @Min(100000000)
    @Max(999999999)
    private Integer phone;

    @NotEmpty
    @Email
    private String email;
       
    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    /**
     * JPA ignorará este campo cuando realice operaciones de persistencia (como insertar o actualizar) en la entidad.
     * Es útil cuando tienes campos en tu entidad que no deseas almacenar en la base de datos,
     *  pero que aún necesitas en tu lógica de negocio.
     * JsonProperty.Access.WRITE_ONLY especifica que el campo admin solo debe ser considerado durante la deserialización 
     * (cuando se convierte JSON a un objeto Java). Nota: ver en Postman campo admin: true || false
     * Esto significa que el campo admin puede ser establecido a partir de datos JSON entrantes, 
     * pero no será incluido en la salida JSON cuando el objeto Java se serialice a JSON.
     */
    
    @Transient  
   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Nos permite luego rellenar el campo en la clase Entity o UserRequest
    private boolean admin;

    @NotBlank
    private String password;
    /**
     * Al tener el usuario varios roles, se establece una relación de muchos a muchos por ello
     * creamos una lista de roles que se mapea a la tabla roles.
     */
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"}) //Evita la recursividad en la serialización JSON
    @ManyToMany(fetch = FetchType.LAZY)
    /**
     * Con @JoinTable indicamos que la relación entre las tablas users y roles se mapea a la tabla users_roles.
     * Con @JoiColummn conseguimos mapear la relación entre las tablas users y users_roles
     * Luego usamos inverseJoinColumns para mapear la relación inversa entre las tablas roles y users_roles
     * Al tener aqui los roles de Role que está mapeada a la tabla roles, se establece la relación entre users y roles
     * Luego tenemos que indicar los campos únicos user_id y role_id con la anotación @UniqueConstraint.
     */
    @JoinTable(name = "users_roles", 
    joinColumns = {@JoinColumn(name="user_id")},
    inverseJoinColumns = {@JoinColumn(name="role_id")},
    uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})}
    ) 
    private List<Role> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getPhone() {
        return phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public List<Role> getRoles() {
        return roles;
    }


    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    

}
