# Build

run './gradlew :module_name:fatJar'

Build creates executable jar in module_name/build/libs/ folder.

# Run

run 'java -jar module_name/build/libs/module_name-executable.jar args' where args are:
* for Indexer: path_to_indexed_folder
* for Searcher: path_to_index_file, search_query
