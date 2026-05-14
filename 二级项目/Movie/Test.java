package movie;

public class Test {
    public static void main(String[] args) {
        // 目标：完成电影案例
        // 1.创建电影对象：定义电影类
        // 2.创建电影操作对象：负责对象电影数据处理（上，下架，查询）
        MovieService movieService = new MovieService();
        movieService.start();
    }
}
