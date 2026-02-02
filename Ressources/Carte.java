public class Carte {
    Symbole symbole;
    int valeur;

    Carte(Symbole s, int v){
        this.symbole = s;
        this.valeur = v;
    }

    char symbole(){
        if(this.symbole == Symbole.CARREAU){
            return '♦';
        }
        else if(this.symbole == Symbole.COEUR){
            return '♥';
        }
        else if(this.symbole == Symbole.TREFLE){
            return '♣';
        }else{
            return '♠';
        }
    }
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