How it work 
---
- Generate several threads depend on 
how many employees in call center.
- A singleton data sharing object(PCP) will contain the phone calls. It will using 3 list to handle different position's TODO.
- Start digest phone calls(multi-thread).

How to
---
Assume you are using linux-liked os
````
$cd {project-dir}/target/
````
Run commands below
````
$java -jar assignment-1.0-SNAPSHOT.jar
````
or

````
$java -jar assignment-1.0-SNAPSHOT.jar {numbers-of-freasher} {numbers-of-team-leader} {numbers-of-product-manager}
````
Then it will dumping hole process and exit.