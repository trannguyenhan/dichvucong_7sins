package crawler.main;

import crawler.resource.ServicesLink;

public class Demo {
    public static void main(String[] args) {
        DVC dvc = new DVC();

        String list[] = ServicesLink.LIST;
        for (int i=0; i<list.length; i++){
            dvc.updateService(list[i]);
            dvc.run();
        }
    }
}
