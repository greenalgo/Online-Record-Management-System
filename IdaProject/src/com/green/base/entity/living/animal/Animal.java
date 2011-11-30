package com.green.base.entity.living.animal;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.green.base.entity.living.related.enums.Gender;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "animal", discriminatorType = DiscriminatorType.STRING)
public abstract class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Gender gender;
    protected Boolean isBiped;// having 2 legs
    protected Boolean iswild;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date dateOfBirth;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date dateOfDeath;
    protected String animalDescription;
    protected String reasonForDeath;

    public Animal() {
    }

    public Animal(Gender gender, Boolean isBiped, Boolean iswild,
            Date dateOfBirth, String animalDescription) {
        super();
        this.gender = gender;
        this.isBiped = isBiped;
        this.iswild = iswild;
        this.dateOfBirth = dateOfBirth;
        this.animalDescription = animalDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnimalDescription() {
        return animalDescription;
    }

    public void setAnimalDescription(String animalDescription) {
        this.animalDescription = animalDescription;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getIsBiped() {
        return isBiped;
    }

    public void setIsBiped(Boolean isBiped) {
        this.isBiped = isBiped;
    }

    public Boolean getIswild() {
        return iswild;
    }

    public void setIsWild(Boolean iswild) {
        this.iswild = iswild;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public String getReasonForDeath() {
        return reasonForDeath;
    }

    public void setReasonForDeath(String reasonForDeath) {
        this.reasonForDeath = reasonForDeath;
    }

    public void setIswild(Boolean iswild) {
        this.iswild = iswild;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Animal other = (Animal) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
