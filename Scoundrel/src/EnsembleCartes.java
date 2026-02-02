import java.util.Scanner;

public class EnsembleCartes{
    Carte[] ensembleCartes;

    EnsembleCartes(int i){
        this.ensembleCartes = new Carte[i];
    }

    int cartesRestantes(EnsembleCartes donjon){
        int i = 0;
        while(i < this.ensembleCartes.length && this.ensembleCartes[i] != null){
            i++;
        }
        for(int j = 0; j < donjon.ensembleCartes.length; j++){
            if(donjon.ensembleCartes[j] != null){
                i++;
            }
        }
        return i;
    }

    void creerPile(){
        for(int i = 0; i < this.ensembleCartes.length; i++){
            if(i <= 12){
              this.ensembleCartes[i] = new Carte(Symbole.COEUR, i+1); 
            }
            else if(i <= 25){
              this.ensembleCartes[i] = new Carte(Symbole.PIQUE, i-12);
            }
            else if(i <= 38){
              this.ensembleCartes[i] = new Carte(Symbole.TREFLE, i-25);
            }else{
              this.ensembleCartes[i] = new Carte(Symbole.CARREAU, i-38); 
            }
        }
    }

    void melangerPile(){
        EnsembleCartes pileMelangee = new EnsembleCartes(52);
        for(int i = 0; i < this.ensembleCartes.length; i++){
            int random = (int)(Math.random() * 52);
            if(this.ensembleCartes[random] != null){
                 pileMelangee.ensembleCartes[i] = this.ensembleCartes[random];
                 this.ensembleCartes[random] = null;
            }else{
                i--;
            }
        }
        this.ensembleCartes = pileMelangee.ensembleCartes;
    }

    void shifting(){
        int i = 0;
        while(i < this.ensembleCartes.length && this.ensembleCartes[i] == null){
            i++;
        }
        for(int j = 0; j < this.ensembleCartes.length-i; j++){
            this.ensembleCartes[j] = this.ensembleCartes[j+i];
            this.ensembleCartes[j+i] = null; 
        }
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < this.ensembleCartes.length; i++){
            if(this.ensembleCartes[i] == null){
                result = result + "null\n";
            }else{
                result = result + this.ensembleCartes[i].symbole + " " + this.ensembleCartes[i].valeur + "\n";
            }
        }
        return result;
    }

    void newDonjon(EnsembleCartes ouPiocher){
        int i = 0;
        if(this.ensembleCartes[0] == null){
            this.ensembleCartes[0] = ouPiocher.ensembleCartes[i];
            ouPiocher.ensembleCartes[i] = null;
            i++;
        }
        if(this.ensembleCartes[1] == null){
            this.ensembleCartes[1] = ouPiocher.ensembleCartes[i];
            ouPiocher.ensembleCartes[i] = null;
            i++;
        }
        if(this.ensembleCartes[2] == null){
            this.ensembleCartes[2] = ouPiocher.ensembleCartes[i];
            ouPiocher.ensembleCartes[i] = null;
            i++;
        }
        if(this.ensembleCartes[3] == null){
            this.ensembleCartes[3] = ouPiocher.ensembleCartes[i];
            ouPiocher.ensembleCartes[i] = null;
        }
    }

    boolean isEmpty(EnsembleCartes donjon){
        for(int i = 0; i < donjon.ensembleCartes.length; i++){
            if(donjon.ensembleCartes[i] != null){
                return false;
            }
        }
        return this.ensembleCartes[0] == null;
    }

    void skip(EnsembleCartes donjon){
        for(int i = 0; i < donjon.ensembleCartes.length; i++){
            int j = 0;
            while(this.ensembleCartes[j] != null){
                j++;
            }
            this.ensembleCartes[j] = donjon.ensembleCartes[i];
            donjon.ensembleCartes[i] = null;
        }
    }

    boolean isDone(){
        int n = this.ensembleCartes.length;
        for(int i  = 0; i < this.ensembleCartes.length; i++){
            if(this.ensembleCartes[i] == null){
                n--;
            }
        }
        if(n == 1){
            return true;
        }else{
            return false;
        }
    }

    void jouerCarte(Player player, int i){
        Carte laCarte = this.ensembleCartes[i];
        if(laCarte == null){
            Scoundrel.boxln("Vous essayez d'utiliser... rien. Etes-vous devenu fou ?");
        }
        else if(laCarte.symbole == Symbole.COEUR){
            jouerCoeur(player, i);
        }
        else if(laCarte.symbole == Symbole.PIQUE || laCarte.symbole == Symbole.TREFLE){
            jouerNoir(player, i);
        }else{
            jouerCarreau(player, i);
        }
    }

    void jouerCoeur(Player player, int i){
        Carte laCarte = this.ensembleCartes[i];
        if(!player.alreadyHealed){
            int pvGagnee = player.pv;
            player.pv += laCarte.valeur;
            if(player.pv > 20){
                player.pv = 20;
            }
            pvGagnee = player.pv - pvGagnee;
            player.alreadyHealed = true;
            Scoundrel.boxln("Vous avez regagné " + pvGagnee + " pvs.");
            this.ensembleCartes[i] = null;
        }else{
            Scoundrel.boxln("Il ne faut pas abuser des bonnes choses. Cependant, vous venez tout de même de gacher cette denrée précieuse.");
            this.ensembleCartes[i] = null;
        }

    }
    
    void jouerNoir(Player player, int i){
        Carte laCarte = this.ensembleCartes[i];
        if(player.maxDamage >= this.ensembleCartes[i].valeur && player.arme != 0){
            Scanner input = new Scanner(System.in);
            System.out.print("Voulez vous parrer (1) ou encaisser (2) : ");
            int choix = Scoundrel.momentDeSaisie(2, input);
            if(choix == 1 && laCarte.valeur <= player.maxDamage){
                int degatsPris = laCarte.valeur - player.arme;
                if(degatsPris < 0){
                    degatsPris = 0;
                }
                player.pv = player.pv - degatsPris;
                player.maxDamage = laCarte.valeur - 1;
                Scoundrel.boxln("Vous avez pris " + degatsPris + " degats.");
                this.ensembleCartes[i] = null;
            }else{ 
                player.pv = player.pv - laCarte.valeur;
                Scoundrel.boxln("Vous avez pris " + laCarte.valeur + " degats.");
                this.ensembleCartes[i] = null;
            }
        }else{
            player.pv = player.pv - laCarte.valeur;
            Scoundrel.boxln("Par incapcité de bloquer, vous êtes obliger de subir. Encaissant " + laCarte.valeur + " degats.");
            this.ensembleCartes[i] = null;
        }
    }

    void jouerCarreau(Player player, int i){
        Carte laCarte = this.ensembleCartes[i];
        player.arme = laCarte.valeur;
        player.maxDamage = 13;
        Scoundrel.boxln("Votre nouvelle arme est de " + laCarte.valeur + " points de défenses.");
        this.ensembleCartes[i] = null;
    }

    void printlnDonjon(){
        for(int i = 0; i < this.ensembleCartes.length; i++){ //1
            if(this.ensembleCartes[i] == null){
                System.out.print("┌╶╶╶╶╶╶╶╶╶╶╶╶╶╶╶┐ "); //18 de taille
            }else{
                System.out.print(" ████████████████  ");
            }
        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //2
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }else{
                System.out.print("██              ██ ");
            }
        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //3
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }
            else if(this.ensembleCartes[i].valeur < 10){
                System.out.print("█ " + this.ensembleCartes[i].symbole() + " - " +  this.ensembleCartes[i].valeur + "          █ ");
            }else{
                System.out.print("█ " + this.ensembleCartes[i].symbole() + " - " +  this.ensembleCartes[i].valeur + "         █ ");
            }
        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //4
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }else{
                System.out.print("█                █ ");
            }
        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //5
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }
            else if(this.ensembleCartes[i].symbole == Symbole.COEUR){
                System.out.print("█   ████  ████   █ ");
            }
            else if(this.ensembleCartes[i].symbole == Symbole.CARREAU){
                System.out.print("█   ████         █ ");
            }else{
                System.out.print("█    █▄    ▄█    █ ");
            }

        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //6
            if(this.ensembleCartes[i] == null){
                System.out.print("╷     VIDE.     ╷ "); 
            }
            else if(this.ensembleCartes[i].symbole == Symbole.COEUR){
                System.out.print("█  ████████████  █ ");
            }
            else if(this.ensembleCartes[i].symbole == Symbole.CARREAU){
                System.out.print("█   ██████  ██   █ ");
            }else{
                System.out.print("█                █ ");
            }

        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //7
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }
            else if(this.ensembleCartes[i].symbole == Symbole.COEUR){
                System.out.print("█  ████████████  █ ");
            }
            else if(this.ensembleCartes[i].symbole == Symbole.CARREAU){
                System.out.print("█     ████████   █ ");
            }else{
                System.out.print("█     ▄█▀▀█▄     █ ");
            }

        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //8
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }
            else if(this.ensembleCartes[i].symbole == Symbole.COEUR){
                System.out.print("█     ██████     █ ");
            }
            else if(this.ensembleCartes[i].symbole == Symbole.CARREAU){
                System.out.print("█       ████     █ ");
            }else{
                System.out.print("█                █ ");
            }

        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //9
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ "); 
            }
            else if(this.ensembleCartes[i].symbole == Symbole.COEUR){
                System.out.print("█       ██       █ ");
            }
            else if(this.ensembleCartes[i].symbole == Symbole.CARREAU){
                System.out.print("█     ████  ██   █ ");
            }else{
                System.out.print("█                █ ");
            }

        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //10
            if(this.ensembleCartes[i] == null){
                System.out.print("╷               ╷ ");
            }else{
                System.out.print("██              ██ ");
            }
        }
        System.out.println();
        for(int i = 0; i < this.ensembleCartes.length; i++){ //11
            if(this.ensembleCartes[i] == null){
                System.out.print("└╶╶╶╶╶╶╶╶╶╶╶╶╶╶╶┘ "); 
            }else{
                System.out.print(" ████████████████  ");
            }
        }
        System.out.println();
        System.out.println();
        
    }
    /*
 ████████████████   ████████████████   ████████████████   ████████████████
██              ██ ██              ██ ██              ██ ██              ██ 
█ ♥ - 10         █ █ ♦ - 3          █ █ ♣ - 9          █ █ ♠ - 13         █
█                █ █                █ █                █ █                █
█   ████  ████   █ █   ████         █ █    █▄    ▄█    █ █    █▄    ▄█    █
█  ████████████  █ █   ██████  ██   █ █                █ █                █
█  ████████████  █ █     ████████   █ █     ▄█▀▀█▄     █ █     ▄████▄     █
█     ██████     █ █       ████     █ █                █ █                █
█       ██       █ █     ████  ██   █ █                █ █                █
██              ██ ██              ██ ██              ██ ██              ██ 
 ████████████████   ████████████████   ████████████████   ████████████████

*/
    void printlnPile(){
        System.out.println(" ████████████████ ");
        System.out.println("██              ██");
        System.out.println("█                █");
        System.out.println("█                █");
        System.out.println("█     PILE :     █");
        if(this.cartesRestantes(new EnsembleCartes(4)) < 10){
            System.out.println("█        " + this.cartesRestantes(new EnsembleCartes(4)) + "       █");
        }else{
            System.out.println("█       " + this.cartesRestantes(new EnsembleCartes(4)) + "       █");
        }
        System.out.println("█     CARTES     █");
        System.out.println("█                █");
        System.out.println("█                █");
        System.out.println("██              ██");
        for(int i = 0; i < (this.cartesRestantes(new EnsembleCartes(4))/10)-1; i++){
            System.out.println("██████████████████");
        }
        System.out.println(" ████████████████ ");
    }
}
