package movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {
    // 2.准备容器
    private static List<Movie> movies = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public void start() {
        while (true) {
            // 1.准备操作界面,cmd命令
            System.out.println("====电影信息操作系统====");
            System.out.println("1.上架电影");
            System.out.println("2.下架电影");
            System.out.println("3.查询电影");
            System.out.println("4.封杀明星");
            System.out.println("5.退出");
            System.out.println("6.展示所有电影");
            System.out.println("7.修改电影");
            System.out.println("请输入操作命令：");
            String command = sc.next();
            switch (command) {
                case "1":
                    addMovie();
                    break;
                case "2":
                    removeMovie();
                    break;
                case "3":
                    queryMovie();
                    break;
                case "4":
                    deleteStar();
                    break;
                case "5":
                    System.out.println("退出成功！");
                    return;
                case "6":
                    showMovies();
                    break;
                case "7":
                    updateMovie();
                    break;
                default:
                    System.out.println("输入错误！");
            }
        }
    }

    /**
     * 展示索有电影
     */
    private void showMovies() {
        System.out.println("====所有电影====");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /**
     * 封杀某个明显
     */
    private void deleteStar() {
        System.out.println("====封杀明星====");
        System.out.println("请输入要封杀的明星名称：");
        String name = sc.next();
        for (int i = 0; i < movies.size(); i++){
            Movie movie = movies.get(i);
            if (movie.getActor().contains(name)) {
                movies.remove(i);
                System.out.println("封杀成功！");
                i--; //退一步
            }
        }
        // 删完展示
        showMovies();
    }

    /**
     * 修改电影
     */
    private void updateMovie() {
        System.out.println("====修改电影====");
        System.out.println("请输入要修改的电影名称：");
        String name = sc.next();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            if (movie.getName().equals(name)) {
                System.out.println("请输入新的电影名称：");
                movie.setName(sc.next());
                System.out.println("请输入新的主演：");
                movie.setActor(sc.next());
                System.out.println("请输入新的价格：");
                movie.setPrice(sc.nextDouble());
            }
        }
    }

    /**
     * 根据电影名称查询电影
     */
    private void queryMovie() {
        System.out.println("====查询电影====");
        System.out.println("请输入电影名称：");
        String name = sc.next();
        // 根据名称查对象返回，展示数据
        Movie movie = queryMovieByName(name);
        if (movie != null) {
            System.out.println(movie);
        } else {
            System.out.println("没有找到该电影！");
        }
    }

    // 根据名称查询对象返回
    public Movie queryMovieByName(String name) {
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                return movie; // 找到这个对象
            }
        }
        return null; // 没有找到
    }

    /**
     * 上架电影
     */
    private void addMovie() {
        System.out.println("====上架电影====");
        // 每点击一次上架电影，就是新增一部电影。每部电影是一个对象封装数据的
        // 1.创建电影对象，封装这部电影信息
        Movie movie = new Movie();
        // 2.给电影注入数据
        System.out.println("请输入电影名称：");
        movie.setName(sc.next());
        System.out.println("请输入电影主演：");
        movie.setActor(sc.next());
        System.out.println("请输入电影价格：");
        movie.setPrice(sc.nextDouble());
        System.out.println("请输入电影评分：");
        movie.setScore(sc.nextDouble());
        // 3.把电影对象添加到集合中
        movies.add(movie);
        System.out.println("上架成功！");
    }
    /**
     * 下架电影
     */
    private void removeMovie() {
        System.out.println("====下架电影====");
        System.out.println("请输入电影名称：");
        String name = sc.next();
        Movie movie = queryMovieByName(name);
        if (movie != null) {
            movies.remove(movie);
            System.out.println("下架成功！");
        } else {
            System.out.println("没有找到该电影！");
        }
    }
}

