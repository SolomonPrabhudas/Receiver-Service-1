package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

//import SenderProgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
@SpringBootApplication
@RestController
@EnableJpaRepositories
@EnableDiscoveryClient
public class ReceiverService1Application 
{
  
    private final EmployeeRepository repository;
  
    ReceiverService1Application(EmployeeRepository repository)
    {
       this.repository = repository;
    }

    @RequestMapping(value="/getall", method=RequestMethod.GET)
    //@GetMapping("/employees")
    public List<Employee> all()
    {
       //System.out.println("Getting Data");
       List<Employee> result = repository.findAll();

// No need for these 3 lines
//       List<String> stringList = result.stream()
//               .map(Employee::toString)
//               .collect(Collectors.toList());
       
       System.out.println("Get All Data: "+result.toString());
       return repository.findAll(); //result;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    //@PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee newEmployee)
    {
       //System.out.println("Inserting Data");
       Employee result = repository.save(newEmployee);
       System.out.println("Inserting Data: "+result.toString());
       return repository.save(newEmployee);
       //return "Inserting Data";
    }
   
    @RequestMapping(value="/getone/{id}", method=RequestMethod.GET)
    //@GetMapping("/employees/{id}")
    public Employee one(@PathVariable Long id)
    {
       //System.out.println("Getting Data By Id");
       Employee result = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
       System.out.println("Getting Data By Id: "+result.toString());

       return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
       //return "Getting Data By Id";
    }
 
    @RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
    //@PutMapping("/employees/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
       //System.out.println("Updating Data");

       Employee result = repository.findById(id)
         .map(employee -> {
           employee.setName(newEmployee.getName());
           employee.setRole(newEmployee.getRole());
           return repository.save(employee);
         })
         .orElseGet(() -> {
           newEmployee.setId(id);
           return repository.save(newEmployee);
         });

       System.out.println("Updating Data: "+result.toString());
       return repository.findById(id)
         .map(employee -> {
           employee.setName(newEmployee.getName());
           employee.setRole(newEmployee.getRole());
           return repository.save(employee);
         })
         .orElseGet(() -> {
          newEmployee.setId(id);
           return repository.save(newEmployee);
         });
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    //@DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id)
    {
       //System.out.println("Deleting Data");
       repository.deleteById(id);
       //return "Deleting Data";
    }

    public static void main(String[] args) 
    {
       SpringApplication.run(ReceiverService1Application.class, args);
       //SenderProgram.senderMethods();
    }

 }
