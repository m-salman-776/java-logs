# S3-Like Object Storage Design (Interview Ready)

## 1. High-Level Architecture

    Client
       |
       v
    API Gateway
       |
       v
    Metadata Service (SQL / Distributed KV)
       |
       +----> ObjectLatest (Version Pointer Table)
       |
       v
    Storage Nodes (HDD-backed)
       |
       v
    Disks (Fragments Stored as Files)

------------------------------------------------------------------------

# 2. NON-VERSIONED DESIGN

## Sample Tables

### Bucket

  bucket_id   bucket_name
  ----------- -------------
  B1          docs

### Object

  ----------------------------------------------------------------------------
  object_id   bucket_id   object_key   total_size   chunk_size   chunk_count
  ----------- ----------- ------------ ------------ ------------ -------------
  O1          B1          report.pdf   68157440     10485760     7

  ----------------------------------------------------------------------------

### Chunk

  object_id   chunk_index   size
  ----------- ------------- ----------
  O1          0             10485760
  O1          1             10485760
  O1          2             10485760
  O1          3             10485760
  O1          4             10485760
  O1          5             10485760
  O1          6             5242880

------------------------------------------------------------------------

## Read Queries (Non-Versioned)

### 1️⃣ Get Object Metadata

``` sql
SELECT object_id, total_size, chunk_size, chunk_count
FROM Object
WHERE bucket_id = 'B1'
  AND object_key = 'report.pdf';
```

Result:

  object_id   total_size   chunk_size   chunk_count
  ----------- ------------ ------------ -------------
  O1          68157440     10485760     7

------------------------------------------------------------------------

### 2️⃣ Get Ordered Chunks

``` sql
SELECT chunk_index, size
FROM Chunk
WHERE object_id = 'O1'
ORDER BY chunk_index ASC;
```

------------------------------------------------------------------------

### 3️⃣ Get Fragments for Chunk 0

``` sql
SELECT fragment_index, storage_node_id, disk_id, file_path
FROM Fragment
WHERE object_id = 'O1'
  AND chunk_index = 0;
```

------------------------------------------------------------------------

# 3. VERSIONED DESIGN

## ObjectVersion Table

  -----------------------------------------------------------------------------------------------
  bucket_id   object_key   version_id   object_id   total_size   chunk_count   is_delete_marker
  ----------- ------------ ------------ ----------- ------------ ------------- ------------------
  B1          report.pdf   V1           O1          68157440     7             false

  B1          report.pdf   V2           O2          73400320     7             false
  -----------------------------------------------------------------------------------------------

## ObjectLatest Table

  bucket_id   object_key   latest_version_id
  ----------- ------------ -------------------
  B1          report.pdf   V2

------------------------------------------------------------------------

## Read Queries (Versioned)

### 1️⃣ Get Latest Version

``` sql
SELECT latest_version_id
FROM ObjectLatest
WHERE bucket_id = 'B1'
  AND object_key = 'report.pdf';
```

### 2️⃣ Get Metadata for That Version

``` sql
SELECT object_id, total_size, chunk_count
FROM ObjectVersion
WHERE bucket_id = 'B1'
  AND object_key = 'report.pdf'
  AND version_id = 'V2';
```

------------------------------------------------------------------------

# Interview Summary

-   Immutable object_id per version\
-   Chunking with deterministic offsets\
-   Erasure coding (4+2) for durability\
-   Metadata-driven reconstruction\
-   Versioning implemented purely at metadata layer
