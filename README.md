# GenDB - Database Documentation and Maintenance

A web application, that helps developers and product owners to document and maintain complex database setups.

## Features

* define projects, releases, databases, tables and columns outside of the database in a declarative way
* generate DDL to create and seed the database
* generate DDLs to migrate an existing database from one release to another release  forth and back.
* automatically populate tables with any seed data (and generate seed data templates)
* import structure of a database from an existing database automatically

# Why GenDB?

## The Problem

In real life, databases are "designed" with pure drawing tools like draw.io, Visio or similar or even some more enhanced database design tools like dataedo etc. 

What all of these tools are missing is a life cycle support!

Maintaining legacy systems often means to modify or adopt the underlying databases to a new release.
Tools like flyway can help DEV-OPS to easily apply versioned DDL scripts to their databases. 

But: the scripts need to be written by some developer manually!

Furthermore, the documentation often exists separately from the manually written DDL scripts and validating consistency is as well a manual task. There is no "single source of truth" which contains the entire story: the structure, it's documentation and it's life cycle modifications.

## The Solution

With GenDB, developers are able to describe their database within a given project for a given release. 

They can easily generate all necessary DDL scripts to create the described database and load according seed data if necessary.

Once a new release needs modifications within the database, users can create a new release within their projects and 
apply the according modifications within the new release. 

GenDB generates the migration scripts automatically by comparing two given releases (i.e. "migrate to version 2 from version 1") and these scripts 
can be applied to an existing database to migrate it to the new version (i.e. with tools like flyway etc.).

# Documentation

REST API documentation is available <a href="https://xlthor.github.io/gendb/" target="_blank">here</a>!

# Current Status of Development

* October 2022:<br>
Currently, the entire logic of the system will be implemented "headless" ("API first" approach) as REST APIs. The backend is a spring-boot application, providing all necessary APIs to control the entire System. A frontend (REACT, ANT, ...) will be applied in the next step as soon as this is finished as an "MVP".


