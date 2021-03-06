import java.io.File;
import java.util.Scanner;

public class MapLoader {
	public static Entity[][][] loadMap(String input)throws Exception{
		String path = "C:\\Users\\daksh\\IdeaProjects\\HackerMan\\src\\maps\\";
		Scanner file = new Scanner(new File(path+input+".txt"));
		int amount = 0;
		while(file.hasNext()) {
			file.nextLine();
			amount++;
		}
		file.close();
		String[][][] stringMap = new String[2][amount][amount];
		file = new Scanner(new File(path+input+".txt"));
		for(int i = 0; i < amount; i++){
			for (int j = 0; j < amount; j++){
				stringMap[0][i][j] = file.next();
			}
		}
		file.close();

		file = new Scanner(new File(path + input + "-g.txt"));
		int amount2 = 0;
		while (file.hasNextLine()){
			file.nextLine();
			amount2++;
		}
		file.close();

		String[] guardsRaw = new String[amount2];

		file = new Scanner(new File(path + input + "-g.txt"));
		for (int i = 0; i < guardsRaw.length; i++){
			guardsRaw[i] = file.nextLine();
		}
		file.close();

		String[][] guardPath = new String[amount2][];
		for (int i = 0; i < guardPath.length; i++){
			guardPath[i] = guardsRaw[i].split("/");
		}

		Pair[][] guards = new Pair[amount2][];
		for (int i = 0; i < guards.length; i++){
			guards[i] = new Pair[guardPath[i].length];
			for (int j = 0; j < guards[i].length; j++){
				System.out.println(guardPath[i][j]);
				String[] temp = (guardPath[i][j]).split(",");
				System.out.println(temp.length);
				guards[i][j] = new Pair(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
			}
		}

		Entity[][][] map = new Entity[2][amount][amount];
		for(int i = 0; i < amount; i++){
			for(int j = 0; j < amount; j++){
				map[0][i][j] = entityParser(stringMap[0][i][j],i,j);
			}
		}

		for(int i = 0; i < guards.length; i++){
			map[1][guards[i][0].x][guards[i][0].y] = new Guard(guards[i][0].x,guards[i][0].y,0,guards[i]);
		}

		return map;
	}

	public static Entity entityParser(String toParse,int i,int j) {
		//Entity Parsing
		String[] temp = toParse.split("/");
		Entity entity;

		// terminal
		if(temp[0].equals("terminal")){
			System.out.println("terminal");
			entity = new Terminal(i,j,0,1,0);
		}
		// lasernode
		else if (temp[0].equals("lasernode")){
			System.out.println("laser node");
			entity = new LaserNode(i,j,0);
		}
		// warp/mapname/connected x/connected y
		else if (temp[0].equals("warp")){
			System.out.println("warp");
			entity = new WarpTile(i,j,0,temp[1],Integer.parseInt(temp[2]),Integer.parseInt(temp[3]));
		}
		// wall
		else if (temp[0].equals("wall")){
			System.out.println("wall");
			entity = new Wall(i,j,0);
		}
		// null
		else if (temp[0].equals("null")){
			System.out.println("null");
			entity = null;
		}
		else {
			entity = null;
		}

		return entity;
	}
}

