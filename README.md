# Utilisation du projet :
Pour réaliser le chiffrage d'un fichier, il faut lancer le programme avec la commande suivante :
```java -jar artifacts/CBCProject_jar/CBCProject.jar <clé> <vecteur> <fichier à déchiffrer>```

Le premier argument correspond à la clé permettant de savoir comment chiffrer le fichier. Le second argument correspond au vecteur d'initialisation. Le troisième argument correspond au fichier à chiffrer.

### Résultat:
Le programme va générer un fichier chiffré là où se trouve le fichier à chiffrer avec l'extension .encrypted.
Ce fichier contient le texte déchiffré.