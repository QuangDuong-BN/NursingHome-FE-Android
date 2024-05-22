package com.example.nursinghome_android.entityDTO;

public class User {
    private int id;
    private String familyMember;
    private String name;
    private String phone;
    private String role;
    private String username;
    private String password;
    private String email;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String imageUrl;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private Object authorities;
    private boolean accountNonLocked;

    public User(int id, String familyMember, String name, String phone, String role, String username, String password, String email, String address, String dateOfBirth, String gender, String imageUrl, boolean enabled, boolean credentialsNonExpired, boolean accountNonExpired, Object authorities, boolean accountNonLocked) {
        this.id = id;
        this.familyMember = familyMember;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.enabled = enabled;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonExpired = accountNonExpired;
        this.authorities = authorities;
        this.accountNonLocked = accountNonLocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Object getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Object authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}
