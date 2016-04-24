#HETESIM Semantic Path Search#

##PROP Project 42.3 Group##

#####Contributors:#####

- Andreu Rodríguez i Donaire (@anrodon)
- Ian Sanginés-Uriarte Muñoz (@iansangines)
- Nicola Bafundi (@Nico152)
- Sergi Canal (@shierve)

#####Project Description#####

The 42.3 PROP group has done an interactive search based on semantic relevance of the paths defined by the relations of a graph based on the following outline nodes.

The nodes of our graph, as shown in the diagram represent authors, conferences, publications and topics. Apart from that, conceptually, we have paths that define relationships between entities that represent all possible combinations (logical) between the different entities of our graph. A path between two nodes (may be of the same type) is simply a sequence of arcs (can be a cycle). For example, we can define a relationship of co-authorship across the road AUTHOR -> PAPER -> AUTHOR with the restriction that the two authors are not the same. Another example would be the relationship shared theme between two authors across the road AUTHOR -> PAPER -> TERM -> PAPER -> AUTHOR also restricting the two authors are not the same, although the paper if it can be.

The user will ask for a relevance search with the path he has chosen, but in the event that this search has no logic, there will be a pre-defined input file which will be read at the beginning of the implementation of program, where there will be some specific types of selectable paths on which to make a query.

To find the relevant entities related to a particular entity a certain way, the user can perform a simple query or a filtered query. Depending on the nature of the query can be necessarily simple or filtered, although it is not a condition overall.

In a simple query, the user will choose a path or select one of the predefined initial entity and our program will return a list of entities ordered by relevance.

In a filtered query, users will choose a path or select one of the predefined initial entity and certain additional parameters related to the presentation of data, such as maximum number of results to show a range of minimum and maximum relevance, subsets of relations, etc, and our program will return a list of entities ordered by relevance using filters suitable for parameter.

Later, the user can edit the presentation of the results by number or filter results by relevance minimum or maximum alphabetical order...

The data from our program will read text files at the start of program execution. These data files can be modified at runtime by the user by adding new entities and direct relationships between entities, deleting them or changing them, for example, a user can add a new author and a new relationship or publication without each other. In the case of deleting data if a relationship is orphaned, the whole erased.

You also have the option to undo these changes to a certain limit and sometimes for no longer interested to keep these changes.


#####How to compile#####

Just execute:

`make`


#####How to execute#####

Just execute:

`make run`

#####How to clean project#####

Just execute:

`make clean`