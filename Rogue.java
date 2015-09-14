/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogue;

import java.util.Random;
import javax.swing.JOptionPane;


/**
 *
 * @author MCarey
 */
public class Rogue {

    public static final int
        RIEN = 0,
        DEBUT = 1,
        CORRIDOR = 2,
        PIECE = 3,
        CROISEMENT = 4,
        CULDESAC = 5,
        VIE = 6,
        FIN = 7;
    
    private int x;
    private int y;
    private int vie;
    private int force;
    private int armure;
    private Random rand;
    
    private int carte[][] = {
        {DEBUT,RIEN,PIECE,PIECE,PIECE,RIEN,RIEN,CORRIDOR,CULDESAC,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN},
        {CORRIDOR,CORRIDOR,PIECE,PIECE,PIECE,CORRIDOR,CORRIDOR,CORRIDOR,RIEN,RIEN,PIECE,PIECE,VIE,PIECE,RIEN,RIEN},
        {RIEN,RIEN,RIEN,CORRIDOR,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,PIECE,PIECE,PIECE,PIECE,CORRIDOR,RIEN},
        {CORRIDOR,CORRIDOR,CORRIDOR,CROISEMENT,CORRIDOR,CORRIDOR,CORRIDOR,RIEN,RIEN,RIEN,RIEN,CORRIDOR,RIEN,RIEN,CORRIDOR,RIEN},
        {CORRIDOR,RIEN,RIEN,RIEN,RIEN,RIEN,CORRIDOR,CORRIDOR,CORRIDOR,CORRIDOR,CORRIDOR,CORRIDOR,RIEN,RIEN,CULDESAC,RIEN},
        {CORRIDOR,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,CORRIDOR,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN},
        {CORRIDOR,CORRIDOR,CORRIDOR,CORRIDOR,RIEN,RIEN,RIEN,PIECE,PIECE,PIECE,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN},
        {RIEN,RIEN,RIEN,CORRIDOR,CORRIDOR,CORRIDOR,CORRIDOR,PIECE,PIECE,PIECE,CORRIDOR,CORRIDOR,CORRIDOR,CROISEMENT,CORRIDOR,CULDESAC},
        {RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,PIECE,PIECE,PIECE,RIEN,RIEN,RIEN,CORRIDOR,RIEN,RIEN},
        {RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,CORRIDOR,RIEN,RIEN},
        {RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,PIECE,PIECE,PIECE,RIEN},
        {RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,PIECE,FIN,PIECE,RIEN},
        {RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,PIECE,PIECE,PIECE,RIEN},
    };
    
    public Rogue() {
        x = 0;
        y = 0;
        vie = 100;
        force = 5;
        armure = 1;
        rand = new Random();
    }
    
    public String GenereDeplacement() {
        boolean multiple = false;
        String deplacement = "Vous pouvez aller ";
        
        if(y > 0 && carte[y-1][x] != RIEN)  {
            deplacement += "au nord";
            multiple = true;
        }
        if(y < 12 && carte[y+1][x] != RIEN) {
            deplacement += (multiple ? ", " : "") + "au sud";
            multiple = true;
        }
        if(x < 15 && carte[y][x+1] != RIEN) {
            deplacement += (multiple ? ", " : "") + "à l'est";
            multiple = true;
        }
        if(x > 0 && carte[y][x-1] != RIEN)  {
            deplacement += (multiple ? " et " : "") + "à l'ouest";
        }
        return deplacement;
    }
    
    public int GenereDommage(int forceAttaque)
    {
        int dommage = rand.nextInt(forceAttaque) - rand.nextInt(armure);;
        
        if(dommage > 0) {
            vie -= dommage;
            return dommage;
        }
        
        return 0;
    }
    
    public void Combat(int monstre) {
        String nom = "", message, action;
        boolean feminin = false;
        int vieMonstre = monstre;
        int dommage, dommageMonstre;
        
        switch(monstre) {
            case 1:
                nom = "Slime";
                feminin = true;
                break;
            case 2:
                nom = "Slime rouge";
                feminin = true;
                break;
            case 3:
            case 4:
            case 5:
                nom = "Gargouille";
                feminin = true;
                break;
            case 6:
            case 7:
                nom = "Goblin";
                break;
            case 8:
            case 9:
                nom = "Zombie";
                break;
            case 10:
                nom = "Mommie";
                break;
            case 11:
            case 12:
            case 13:
                nom = "Troll";
                break;
            case 14:
                nom = "Lyche";
                feminin = true;
                break;
            case 15:
                nom = "Sorcier";
                break;
            case 16:
            case 17:
            case 18:
                nom = "Dragon";
                break;
            case 19:
            case 20:
                nom = "Barlok";
                break;
        }
       
        while(vie > 0 && vieMonstre > 0)
        {
            message = (feminin ? "Une " : "Un ") + nom + " vous attaque. Vous avez " + 
                    vie + " points de vie. Vous pouvez attaquer ou courrir.";
            action = JOptionPane.showInputDialog(message);
            if(action == null)
            {
                action = "courrir";
            }
            switch(action)
            {
                case "attaquer":
                    if(monstre > force) {
                        dommage = GenereDommage(monstre);
                        if(vie <= 0)
                            break;
                        vieMonstre -= dommageMonstre = rand.nextInt(force);
                    } else {
                        vieMonstre -= dommageMonstre =  rand.nextInt(force);
                        if(vieMonstre < 0)
                            break;
                        dommage = GenereDommage(monstre);
                    }
                    message = "Le monstre vous faite " + dommage + " "
                            + "dommages. Vous lui faite " + dommageMonstre + " dommages.";
                    break;
                default:
                case "courrir":
                    if(rand.nextInt(3) > 1)
                    {
                        message = "Vous réussissez à fuir.";
                        return;
                    } else {
                        dommage = GenereDommage(monstre);
                        message = "Votre fuite échoue. Le monstre vous fait " + dommage + " dommages";
                    }
                    break;
            }
            JOptionPane.showMessageDialog(null, message);
            if(vieMonstre <= 0)
            {
                break;
            }
        }
        
        if(vie > 0)
        {
            JOptionPane.showMessageDialog(null, "Vous avez tuez " + (feminin ? "la " : "le ") + nom + ". Votre force augmente à " + ++force);
        }
    }
    
    public void Jouer() {
        boolean fini = false;
        String message = "", action;
        int risqueMonstre = 0, forceMonstre = 1;
        
        do {
            switch(carte[y][x])
            {
                case DEBUT:
                    message = "Vous êtes au début du dongeon. ";
                    break;
                case CORRIDOR:
                    message = "Vous êtes dans un corridor.";
                    risqueMonstre = 10;
                    forceMonstre = 7;
                    break;
                case PIECE:
                    message = "Vous êtes dans une pièce.";
                    risqueMonstre = 2;
                    forceMonstre = 20;
                    break;
                case CROISEMENT:
                    message = "Vous êtes à un croisement.";
                    risqueMonstre = 10;
                    forceMonstre = 10;
                    break;
                case CULDESAC:
                    message = "Vous êtes à un cul de sac";
                    risqueMonstre = 5;
                    forceMonstre = 7;
                    break;
                case VIE:
                    message = "Vous avez trouvé la fontaine de vie. Votre vie remonte à 100 point.";
                    vie = 100;
                    break;
                case FIN:
                    message = "Vous avez trouvé la sortie.";
                    fini = true;
                    break;
            }
            
            if(fini)
                break;
            
            if(risqueMonstre > 0 && rand.nextInt(risqueMonstre) > risqueMonstre / 2 ) {
                JOptionPane.showMessageDialog(null, message);
                
                Combat(rand.nextInt(forceMonstre) + 1);
                
                if(vie == 0)
                    break;
                
                message = "";
            }
            
            message = message.concat(GenereDeplacement());
            
            action = JOptionPane.showInputDialog(message);
            if(action != null)
                switch(action){
                    case "nord":
                        if(y > 0 && carte[y-1][x] != RIEN)  {
                            y--;
                        }        
                        break;
                    case "sud":
                        if(y < 12 && carte[y+1][x] != RIEN) {
                            y++;
                        }
                        break;
                    case "est":
                        if(x < 15 && carte[y][x+1] != RIEN) {
                            x++;
                        }
                        break;
                    case "ouest":
                        if(x > 0 && carte[y][x-1] != RIEN)  {
                            x--;
                        }
                        break;
                    case "quitter":
                        vie = 0;
                        break;
                    default:
                        break;
                }
            else
                vie = 0;
        } while ( vie > 0 && !fini);
        
        if(fini) {
            JOptionPane.showMessageDialog(null, "Félicitation vous gagné");
        } else {
            JOptionPane.showMessageDialog(null, "Vous avez perdu");
        }
    }
    
}
