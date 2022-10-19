# GenDB - Database Documentation and Maintenance

A web application, that helps developers and product owners to document and maintain complex database setups.

## Features

* define projects, releases, databases, tables and columns outside of the database in a declarative way
* generate DDL to create and seed the database
* generate DDLs to migrate an existing database from one release to another release  forth and back.
* import structure of a database from an existing database automatically

# Why GenDB?

## The Problem

In real life, databases are "designed" with tools like draw.io, Visio or similar or even some more enhanced tools like dataedo etc. 
What all of these tools are missing is a life cycle support: maintaining legacy systems often means to modify or adopt the underlying databases to a new release.
Tools like flyway can help dev-ops to easily apply versioned DDL scripts to their databases. But: the scripts need to be written by some developer manually!

Furthermore, the documentation often exists separately from the manually written DDL scripts and validating consistency is as well a manual task.

## Solution


With GenDB, developers are able to describe their database within a given project for a given release. They can easily generate all necessary 
DDL scripts to create the described database. Once a new release need modifications within the database, users can create a new release within their projects and 
apply the according modifications with the new release. GenDB the generates the migration scripts automatically and these the can be applied (i.e. with tools like flyway etc.)

# Current Status of Development

* October 2022: Currently, the entire logic of the system will be implemented "headless" ("API first" approach).

