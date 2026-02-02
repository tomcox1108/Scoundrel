/*
Dernier bug a regler : fin de partie, le jeu bug car le donjon n'est pas complet.

plan du jeu :

type Carte ayant les infos de la carte.
soit :
symbole
valeur

tableau de Cartes représentant la pile (mélangé) :
    en gros, je vais faire une boucle for qui va, pour tout carte,
    prendre un nb aléatoire de 0 a length-1. Si la case est vide, 
    la carte est copiée.
tableau des Cartes en jeu (vide)

début du jeu :
Un tour : 
réveler les cartes (cartes en jeu) toString en ascii soigné
les cartes du tableau de la pile avances d'autants de cartes révélés.

Si pas skip :
    le joueur choisi une carte
    calcul des valeurs (pv), textes etc... (lore de monstre avec fichiers CSV)
    (3 fois)

ou alors il skip. Dans ce cas :
    les cartes sont remises a l'arriere du paquet.

Se finit quand le paquet est vide ou quand le joueur a 0 de vie.

*/

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

class Scoundrel{
    public static void main(String[] args){
        int choix;
        do{

            /*ecran titre */
            Scanner entry = new Scanner(System.in);
            boxln("Bienvenue dans Scoundrel.");
            entry.nextLine();

            /*menu */
            System.out.print("1 - Jouer. \n2 - Les règles. \n3 - Quitter. \nQue voulez vous faire ? ");
            choix = momentDeSaisie(3, entry);
            if(choix == 1){

                /*creation de la pile, du donjon et du joueur*/
                EnsembleCartes pile = new EnsembleCartes(52);
                pile.creerPile();
                pile.melangerPile();
                
                EnsembleCartes donjon = new EnsembleCartes(4);
        
                Player player = new Player();
        
                /*creation du jeu */
                while(player.pv > 0 && !pile.isEmpty(donjon)){
                    /*intro */
                    donjon.newDonjon(pile);
                    pile.shifting();
                    ligneln(50, '─');
                    pile.printlnPile();
                    accueilSalle(donjon);
                    donjon.printlnDonjon();
                    player.printlnPlayer();
                    
                    /*tour de jeu. Un donjon. */
                    do{
                        System.out.print("Choisissez la carte à jouer. De 1 à 4. \n5 pour fuir cette salle. : ");
                        choix = momentDeSaisie(5, entry);
                        if(choix != 5){
                            donjon.jouerCarte(player, choix-1);
                            boxln("Encore deux... Une fois engagé, vous ne pouvez plus fuir.");
                            while(!donjon.isDone() && player.pv > 0){
                                donjon.printlnDonjon();
                                player.printlnPlayer();
                                System.out.print("Votre choix : ");
                                choix = momentDeSaisie(4, entry);
                                donjon.jouerCarte(player, choix-1);
                            }
                        }else if(player.canSkip != 0){
                            pile.skip(donjon);
                            player.canSkip = -1;
                        }else{
                            boxln("Votre misérable vie de fuyard. Répugnant. Recommencez.");
                        }
                    }while(choix == 5 && player.canSkip == 0);
                    
                    /*reset */
                    player.canSkip ++;
                    player.alreadyHealed = false;
                }
        
                /*fin */
                if(player.pv <= 0){
                    boxln("En ayant été trop courageux, malgré votre force ridicule, en vous entrainant dans le monde de Scoundrel, vous avez péri. Honte à vous.");
                }else{
                    boxln("Une victoire est une victoire. Félicitation. Mais ne vous réjouissez pas trop, votre monde est sans doute une toute autre épreuve.");
                }
            }
            else if(choix == 2){
                lesRegles();
            }
        }while(choix != 3);
    }

    public static int momentDeSaisie(int max, Scanner input){
        String choix;
        do{
            choix = input.nextLine();
            if(!(controleSaisie(choix, max))){
                boxln("Mauvaise saisie.");
            }
        }while(!(controleSaisie(choix,max)));
        return stringToInt(choix);
    }

    static boolean controleSaisie(String choix, int maxInt){
        if(choix.isEmpty()){
            return false;
        }
        else if(!onlyInts(choix)){
            return false;
        }
        else if(!isIn(1,maxInt,choix)){
            return false;
        }
        return true;
    }

    static boolean isIn(int min, int max, String chaine){
        if(stringToInt(chaine) > max || stringToInt(chaine) < min){
            return false;
        }
        return true;
    }

    static int stringToInt(String chaine){
        int result = 0;
        for(int i = chaine.length()-1; i <= 0; i++){
            result = result + ((chaine.charAt(i)-'0')*(int)Math.pow(10, i));
        }
        return result;
    }

    static boolean onlyInts(String chaine){
        for(int i = 0; i < chaine.length(); i++){
            if(chaine.charAt(i) > '9' || chaine.charAt(i) < '0'){
                return false;
            }
        }
        return true;
    }

    public static void boxln(String chaine){
        System.out.print("█╔═");
        ligne(46, '═');
        System.out.println("═╗");
        String mot;
        int longueur = 0; //voir si on a print plus que ce qui fallait
        for(int j = 0; j < chaine.length(); j++){
            mot = "";
            if(longueur == 0){
                System.out.print("█║ ");
            }
            while(j < chaine.length() && chaine.charAt(j) != ' '){
                mot = mot + chaine.charAt(j);
                j++;
            }
            if(mot.length() + longueur <= 46){
                System.out.print(mot + " ");
                longueur += mot.length() + 1;
            }else{
                for(int i = 0; i < 46 - longueur; i++){
                    System.out.print(" ");
                }
                j = j - mot.length()-1;
                if(47 - longueur == 0){
                    System.out.println("║");
                }else{
                    System.out.println(" ║");
                }
                longueur = 0;
            }
        }
        for(int i = 0; i < 46 - longueur; i++){
            System.out.print(" ");
        }
        System.out.println(" ║");
        System.out.print("█╚═");
        ligne(46, '═');
        System.out.println("═╝");
    }

    static void ligne(int n, char c){
        for(int i = 0; i < n; i++){
            System.out.print(c);
        }
    }

    static void ligneln(int n, char c){
        for(int i = 0; i < n; i++){
            System.out.print(c);
        }
        System.out.println();
    }

    static void accueilSalle(EnsembleCartes donjon){
        int compteNoir = 0;
        int compteCoeur = 0;
        int compteCarreau = 0;
        for(int i = 0; i < donjon.ensembleCartes.length; i++){
            if(donjon.ensembleCartes[i] == null){
                break;
            }
            else if(donjon.ensembleCartes[i].symbole == Symbole.COEUR){
                compteCoeur++;
            }
            else if(donjon.ensembleCartes[i].symbole == Symbole.CARREAU){
                compteCarreau++;
            }else{
                compteNoir++;
            }
        }
        if(compteCarreau + compteCoeur + compteNoir != 4){
            boxln("Voici les restes.");
        }
        else if(compteCarreau == 4){
            boxln("Vous arrivez dans une armurerie.");
        }
        else if(compteCoeur == 4){
            boxln("Vous arrivez dans une salle de jouvence.");
        }
        else if(compteNoir == 4){
            boxln("Vous tombez dans une embuscade.");
        }
        else if(compteNoir == 0){
            boxln("Vous arrivez dans une salle de répis.");
        }
        else if(compteCoeur == 0){
            boxln("Vous arrivez dans une zone de combat.");
        }else{
            boxln("Vous arrivez dans une salle du donjon.");
        }
    }

    static void afficherStats(Player player){ //ptet a remove
        if(player.arme != 0){
            boxln("Votre arme : " + player.arme + ". Elle peut encaisser jusqu'aux monstres de valeur " + player.maxDamage + ".");
        }else if(player.maxDamage == 0){
            boxln("Votre arme était de " + player.arme + ", mais elle est cassée.");
        }else{
            boxln("Vous n'avez pas encore d'arme.");
        }
    }

    static void lesRegles(){
        try (BufferedReader br = new BufferedReader(new FileReader("./txt/regles.txt"))){
        String line;
            while ((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Erreur lecture fichier");
        }
    }
}