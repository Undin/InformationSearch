# Environment #
Java 7

# Indexer #
## Build ##

```
./gradlew :Indexer:jar
```

Build creates executable Indexer.jar  in Indexer/build/libs/ folder.

## Run ##

```
java -jar Indexer/build/libs/Indexer.jar path_to_indexing_folder
```

# Searcher #
## Build ##
```
./gradlew :Searcher:jar
```

Build creates executable Searcher.jar  in Searcher/build/libs/ folder.

## Run ##

```
java -jar Searcher/build/libs/Searcher.jar path_to_index_file search_query
```
### Query ###
Search query has next grammar:

```
expr : expr '|' expr ;
expr : expr '&' expr ;
expr : '(' expr ')' ;
expr : WORD ;
WORD : [a-zA-Z0-9]+ ;
```

All whitespace symbols are ignored.

### Query example ###


```
European & (Community | Union)
```
