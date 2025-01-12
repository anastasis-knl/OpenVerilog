package org.example.verilog_compiler.SelectorScene;

class Animal {
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

// Subclass
class Dog extends Animal {
    private int num ;
    @Override
    public void makeSound() {
        System.out.println("Woof Woof");
    }
    public void change_int(Dog d){
        d.num = 4 ;
    }
    public int get_int(){return this.num;}


}
 class Employee{
    private int salary = 1000 ;
    public int getSalary(){return salary;}
}
 class Probation extends Employee{
    public  int salary  ;
    public int getSalary(){return salary *2 ;  }

}
public class driver_class {
    public static void main(String[] args) {
        Probation x = new Probation() ;
        System.out.println(x.salary)  ;
    }

}