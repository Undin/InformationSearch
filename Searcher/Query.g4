grammar Query;

dis : dis '|' con
    | con
    ;

con : con '&' term
    | term
    ;

term : '(' dis ')'
     | STRING
     ;

STRING : [a-zA-Z0-9]+ ;
WS : [ \t\r\n]+ -> skip ;