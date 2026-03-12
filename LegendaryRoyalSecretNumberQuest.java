import java.util.*;

public class LegendaryRoyalSecretNumberQuest {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    static int language = 1;

    static HashMap<String,Integer> wins = new HashMap<>();

    static class Node{
        int data;
        Node next;

        Node(int d){
            data = d;
        }
    }

    static Node head = null;

    static void addGuess(int g){
        Node n = new Node(g);
        n.next = head;
        head = n;
    }

    static void showGuesses(){

        Node temp = head;

        System.out.print("\n📜 Previous guesses: ");

        while(temp!=null){
            System.out.print(temp.data+" ");
            temp=temp.next;
        }

        System.out.println("\n");
    }

    static int prec(char c){
        if(c=='+'||c=='-') return 1;
        if(c=='*'||c=='/') return 2;
        return 0;
    }

    static String infixToPostfix(String exp){

        Stack<Character> stack = new Stack<>();
        String result="";

        for(char c:exp.toCharArray()){

            if(Character.isLetterOrDigit(c))
                result+=c;

            else if(c=='(')
                stack.push(c);

            else if(c==')'){

                while(stack.peek()!='(')
                    result+=stack.pop();

                stack.pop();
            }

            else{

                while(!stack.isEmpty() &&
                        prec(stack.peek())>=prec(c))
                    result+=stack.pop();

                stack.push(c);
            }
        }

        while(!stack.isEmpty())
            result+=stack.pop();

        return result;
    }

    static class Player{
        String name;
        int attempts;

        Player(String n,int a){
            name=n;
            attempts=a;
        }
    }

    static PriorityQueue<Player> leaderboard =
            new PriorityQueue<>(Comparator.comparingInt(p->p.attempts));

    static String tooHigh(){
        if(language==2) return "Demasiado alto";
        if(language==3) return "Trop élevé";
        if(language==4) return "Zu hoch";
        if(language==5) return "बहुत ज़्यादा";
        return "Too High";
    }

    static String tooLow(){
        if(language==2) return "Demasiado bajo";
        if(language==3) return "Trop bas";
        if(language==4) return "Zu niedrig";
        if(language==5) return "बहुत कम";
        return "Too Low";
    }

    static String invalid(){
        if(language==2) return "Número inválido";
        if(language==3) return "Nombre invalide";
        if(language==4) return "Ungültige Zahl";
        if(language==5) return "अमान्य संख्या";
        return "Invalid number!";
    }

    static String askHint(){
        if(language==2) return "¿Usar pista? (yes / no)";
        if(language==3) return "Utiliser un indice ? (yes / no)";
        if(language==4) return "Hinweis benutzen? (yes / no)";
        if(language==5) return "संकेत उपयोग करें? (yes / no)";
        return "Use hint? (yes / no)";
    }

    static String hintText(boolean even){

        if(language==2)
            return even ? "Pista: número PAR" : "Pista: número IMPAR";

        if(language==3)
            return even ? "Indice: nombre PAIR" : "Indice: nombre IMPAIR";

        if(language==4)
            return even ? "Hinweis: Zahl ist GERADE" : "Hinweis: Zahl ist UNGERADE";

        if(language==5)
            return even ? "संकेत: संख्या सम है" : "संकेत: संख्या विषम है";

        return even ? "Hint: number is EVEN" : "Hint: number is ODD";
    }

    static void playGame(String name){

        head = null;

        System.out.println("\n⚔ Select Difficulty");
        System.out.println("1 Squire (1-50)");
        System.out.println("2 Knight (1-100)");
        System.out.println("3 King (1-500)");

        int diff=sc.nextInt();

        int range=100;

        if(diff==1) range=50;
        if(diff==2) range=100;
        if(diff==3) range=500;

        int secret = rand.nextInt(range)+1;

        int attempts=0;

        boolean hintUsed = false;

        while(true){

            System.out.print("\n🎯 Enter guess (1-"+range+"): ");

            int guess=sc.nextInt();

            if(guess<1||guess>range){
                System.out.println(invalid());
                continue;
            }

            attempts++;

            addGuess(guess);

            if(!hintUsed && attempts%3==0){

                System.out.println(askHint());

                String h=sc.next().toLowerCase();

                if(h.equals("yes")||h.equals("y")){

                    hintUsed=true;

                    System.out.println(hintText(secret%2==0));
                }
            }

            if(guess==secret){

                System.out.println("\n🏆 YOU WON in "+attempts+" attempts!");

                leaderboard.add(new Player(name,attempts));

                wins.put(name,wins.getOrDefault(name,0)+1);

                break;
            }

            else if(guess>secret)
                System.out.println(tooHigh());

            else
                System.out.println(tooLow());
        }

        showGuesses();
    }

    static void dailySpin(){

        int reward = rand.nextInt(5)+1;

        System.out.println("\n🎡 Daily Spin Reward: "+reward+" tokens\n");
    }

    static void showLeaderboard(){

        System.out.println("\n🏆 ROYAL LEADERBOARD\n");

        PriorityQueue<Player> copy =
                new PriorityQueue<>(leaderboard);

        int rank=1;

        while(!copy.isEmpty()){

            Player p=copy.poll();

            System.out.println(rank+". "+p.name+" - "+p.attempts+" attempts");

            rank++;
        }

        System.out.println();
    }

    static void banner(){

        System.out.println("\n⚜══════════════════════════════════⚜");
        System.out.println("     LEGENDARY ROYAL SECRET QUEST");
        System.out.println("⚜══════════════════════════════════⚜\n");
    }

    public static void main(String[] args){

        banner();

        System.out.print("Enter your noble name: ");

        String name=sc.nextLine();

        System.out.println("\n🌍 Select Language");
        System.out.println("1 English");
        System.out.println("2 Español");
        System.out.println("3 Français");
        System.out.println("4 Deutsch");
        System.out.println("5 हिन्दी");

        language=sc.nextInt();

        while(true){

            System.out.println("\n══════ ROYAL MENU ══════");
            System.out.println("1 Start Quest");
            System.out.println("2 Daily Spin");
            System.out.println("3 Leaderboard");
            System.out.println("4 Expression Demo (Stack)");
            System.out.println("5 Exit");

            int choice=sc.nextInt();

            if(choice==1)
                playGame(name);

            else if(choice==2)
                dailySpin();

            else if(choice==3)
                showLeaderboard();

            else if(choice==4){

                System.out.println("\nEnter expression:");

                sc.nextLine();

                String exp=sc.nextLine();

                System.out.println("Postfix: "+infixToPostfix(exp));
            }

            else if(choice==5){

                System.out.println("\n👋 Goodbye Knight!\n");

                break;
            }
        }
    }
}