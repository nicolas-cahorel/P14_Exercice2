Feature: Ajout d'un nouveau client
En tant qu'utilisateur de l'application CRM
Je veux pouvoir ajouter un nouveau client
Afin de gérer mes contacts clients efficacement

Scenario: Ajouter un nouveau client avec succès
Given Je suis sur l'écran d'accueil
When Je navigue vers l'écran d'ajout
And Je saisis le nom du client
And Je saisis l'adresse mail du client
And Je clique sur le bouton pour valider l'ajout
Then Je suis redirigé vers l'écran d'accueil
And Le nouveau client apparait dans la liste
