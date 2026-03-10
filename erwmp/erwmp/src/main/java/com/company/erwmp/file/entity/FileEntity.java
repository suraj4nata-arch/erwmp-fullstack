package com.company.erwmp.file.entity;

import com.company.erwmp.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "files")
@Setter
@Getter
public class FileEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;  //Name user uploaded

    private String storedFileName;  //Safe unique name

    private String contentType;   //image/png, pdf

    private Long size;  //File size

    private String filePath;   //Where file lives
}
