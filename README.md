# Boggarton

Windows: set MAVEN_OPTS="-Djava.library.path=target/natives" </BR>
Unix: export MAVEN_OPTS=-Djava.library.path=target/natives </BR>

mvn install </BR>
mvn compile exec:java -Dexec.mainClass=com.foxcatgames.boggarton.Boggarton