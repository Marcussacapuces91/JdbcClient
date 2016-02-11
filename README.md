# JdbcClient
Client JDBC écrit en Java

## Utilisation
Ligne de commande attendue :
```
   java [-D<property>=<value>] JdbcClient <chemin jdbc>
         -D<property>=<value> : Proprietes definies pour la connection jdbc,
                                comme <user> ou <password>, suivant les valeurs
                                acceptees par le driver JDBC designe dans le chemin jdbc
 ```
 
Après le lancement de l'application, les requêtes SQL sont saisies
en ligne de commande et terminées par un point-virgule. Pour terminer 
l'application utiliser le caractère de fin de fichier (^Z).

## Licence 
DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE - Version 2, December 2004

<a href="http://www.wtfpl.net/"><img
       src="http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-4.png"
       width="80" height="15" alt="WTFPL" /></a>
