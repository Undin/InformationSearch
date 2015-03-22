# Environment #
Java 7

# Indexer #
## Build ##

```
#!bash
./gradlew :Indexer:jar

```

Build creates executable Indexer.jar  in Indexer/build/libs/ folder.

## Run ##

```
#!bash
java -jar Indexer/build/libs/Indexer.jar path_to_indexing_folder

```

# Searcher #
## Build ##
```
#!bash
./gradlew :Searcher:jar

```

Build creates executable Searcher.jar  in Searcher/build/libs/ folder.

## Run ##

```
#!bash
java -jar Searcher/build/libs/Searcher.jar path_to_index_file search_query

```
### Query ###
Search query has next grammar:

```
#!antlr4

expr : expr '|' expr ;
expr : expr '&' expr ;
expr : '(' expr ')' ;
expr : WORD ;
WORD : [a-zA-Z0-9]+ ;
```

All whitespace symbols are ignored.

### Query example ###


```
#!bash

European & (Community | Union)
```