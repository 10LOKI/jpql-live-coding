# Gestion de commandes — Spring Data JPA

Projet pédagogique Java 17 / Spring Boot 4.1.1. La partie 1 et les exercices 11 à 15 sont implémentés : recherches dérivées, JPQL, navigation, jointures, fetch, agrégations, regroupements et DTO avec pagination.

## Démarrage

Prérequis : JDK 17 ou supérieur et Maven. Le script Windows mvnw.cmd utilise Maven local s’il est disponible dans .tools, sinon Maven installé sur le système. Il ne télécharge pas Maven.

```bash
mvn test
mvn package
java -jar target/gestion-commandes-0.0.1-SNAPSHOT.jar
```

Sur cet ordinateur Windows, les scripts permettent aussi de travailler hors connexion après préparation des dépendances :

```powershell
.\tester.cmd
.\lancer.cmd
```

Le profil local utilise H2 en mémoire : pas de Docker nécessaire. DemoRunner affiche les résultats des exercices 11 à 15, puis le programme s’arrête normalement avec le code 0. Les données sont recréées à chaque lancement.

## PostgreSQL

```bash
docker compose up -d
java -jar target/gestion-commandes-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres
```

Le profil utilise le schéma d’entraînement jpql_revision avec create-drop : ses tables sont recréées au lancement et supprimées à l’arrêt normal. Utiliser uniquement une base d’exercice. DB_URL, DB_USER et DB_PASSWORD permettent de personnaliser la connexion.

## Organisation

- entities : Client et Commande.
- repositories : requêtes dérivées et JPQL.
- projections : résultats partiels et extrêmes des montants.
- dto : ClientStatDTO, utilisé pour les statistiques.
- services : ClientService et DemoRunner.
- src/test : vérifications de la partie 1 et des exercices 11 à 15.

## Validation

Neuf tests passent avec H2 : recherches, agrégations, pagination, homonymes et clients sans commande. Le profil PostgreSQL est configuré mais n’a pas été exécuté ici.
