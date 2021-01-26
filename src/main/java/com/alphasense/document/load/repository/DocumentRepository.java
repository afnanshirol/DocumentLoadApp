package com.alphasense.document.load.repository;

import com.alphasense.document.load.dto.DocumentDTO;
import com.alphasense.document.load.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This Class is used to create/update/delete @link {Document} object.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
