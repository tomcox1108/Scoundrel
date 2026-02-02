public class Player {
    int pv = 20;
    int arme = 0;
    int maxDamage = 13;
    int canSkip = 2;
    boolean alreadyHealed = false;

    void printlnPlayer(){
        System.out.println("                             █████████████████");
        System.out.println("                            ██               ██");
        if(this.pv < 10){
            System.out.println("                            █ PV : " + this.pv +"          █");
        }else{
            System.out.println("                            █ PV : " + this.pv +"         █");
        }


        if(this.arme == 0){
            System.out.println("                            █ ARME : Aucune   █");
        }
        else if(this.arme < 10){
            System.out.println("                            █ ARME : " + this.arme +"        █");
        }else{
            System.out.println("                            █ ARME : " + this.arme +"       █");
        }
        
        if(this.maxDamage == 0){
            System.out.println("                            █ maxArme : Cassée█");
        }
        else if(this.maxDamage < 10){
            System.out.println("                            █ maxArme : " + this.maxDamage +"     █");
        }else{
            System.out.println("                            █ maxArme : " + this.maxDamage +"    █");
        }
        System.out.println("                            █                 █");
        System.out.println("                            █    ██     ██    █");
        System.out.println("                            █                 █");
        System.out.println("                            █      █████      █");
        System.out.println("                            ██               ██");
        System.out.println("                             █████████████████");
      
    }
/*
 ████████████████
██              ██
█ PV :           █
█ ARME :         █
█ MaxArme :      █
█                █
█    ██    ██    █
█                █
█      ████      █
██              ██
 ████████████████

*/
}
