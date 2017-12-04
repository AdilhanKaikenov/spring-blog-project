package com.epam.adok.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "unique_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class UniqueIdEntity extends AbstractBaseEntity {
}
