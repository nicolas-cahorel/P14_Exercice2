Feature: Ajout d'un nouveau client

  En tant qu'utilisateur de l'application CRM
  Je veux pouvoir ajouter un nouveau client
  Afin de gérer mes contacts clients efficacement

  Scenario: Ajouter un nouveau client avec succès
    Given Je suis sur l'écran d'accueil
    When Je clique sur le bouton d'ajout
    And Je suis redirigé vers l'écran d'ajout
    And Je saisis les informations du client valides
    And Je clique sur le bouton pour valider l'ajout
    Then Un message de confirmation s'affiche
    And Je suis redirigé vers l'écran d'accueil
    And Le nouveau client est visible dans la liste des clients
