indexing
========
This is an attempt to solve the following coding question:

Given a list of one million <string name, int score> pairs where names 
are valid Java variable names, write two programs and try to optimize their 
efficiency:

1. A Construction Program that produces a serializable data structure D 
(say JSON or ProtocolBuffer).

2. A Query Server Program that reads in serialized D and then accepts user 
queries such that for each query s, it responds with the top 10 names 
(ranked by score) that start with s or contains '_s' (so for example, both 
'revenue' and 'yearly_revenue' match the preﬁx 'rev'). Query answering 
should run in sub-linear time (in terms of the number of names in the input).