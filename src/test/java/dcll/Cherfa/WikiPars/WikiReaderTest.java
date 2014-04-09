package dcll.Cherfa.WikiPars;

import org.tsaap.quesions.impl.DefaultQuiz;
import org.tsaap.questions.QuestionType;

import junit.framework.TestCase;

public class WikiReaderTest extends TestCase {

		public void testWikiReader() throws PasDEntrer, ErreurSyntax{
				WikiReader p = new WikiReader();
				assertNotNull(p);
				WikiReader p2=new WikiReader();
				p2.setInput("{Type the question here..."
				+ " |type=\"[]\"}"
				+ "\n+ The correct answer.\n"
				+ "+ Another correct answer.\n"
				+ "- Wrong or misleading answer.\n"
				+ "\n"
				+ "{Type the question here... |type=\"()\"}"
				+ "\n+ The correct answer."
				+ "\n- Wrong or misleading answer."
				+ "\n- Wrong or misleading answer.");
				assertNull(p2.getQuiz());
				DefaultQuiz dq=(DefaultQuiz) p2.parse();
				assertNotNull(dq);
			}

public void testCheckInputFormat1() throws ErreurSyntax, PasDEntrer {
			int retourEx=0;
			WikiReader p=new WikiReader();
			p.setInput("");
			try{
			p.parse();
			} catch (PasDEntrer n){
			retourEx=1;
			}
			assertEquals(retourEx,1);
			
			p.setInput("{Type the question here... |type=\"()\"}"
				+ "\n- Wrong or misleading answer."
				+ "\n- Wrong or misleading answer.");
			try{
			p.parse();}
			catch (ErreurSyntax n){
			retourEx=2;
			}
			assertEquals(retourEx,2);
}

public void testCheckInputFormat2() throws ErreurSyntax, PasDEntrer {
int retourEx=0;
WikiReader p=new WikiReader();

p.setInput("{ L'intitulé d'une question à choix unique se termine systématiquement par"
+ " |type= } \n"
+ "- elle contient des propositions \n "
+ "- fausses \n "
+ "+ et une unique proposition juste.");
try{
p.parse();}
catch (ErreurSyntax n){
retourEx=3;
}
assertEquals(retourEx,3);

p.setInput("{|type=\"()\"}"
+ " \n- elle contient des propositions \n - fausses \n + et une unique proposition juste.");
try{
p.parse();}
catch (ErreurSyntax n){
retourEx=4;
}
assertEquals(retourEx,4);
}

public void testCheckInputFormat3() throws ErreurSyntax, PasDEntrer {
int retourEx=0;
WikiReader p=new WikiReader();

p.setInput("{ L'intitulé d'une question à choix unique se termine systématiquement par |type=\"()\"}\n"
+ "\n");
try {p.parse();

}
catch (ErreurSyntax n){
retourEx=5;
}
assertEquals(retourEx,5);

p.setInput("{ L'intitulé d'une question à choix unique se termine systématiquement par |type=\"()\"}\n"
+ "- fausses \n- fausses\n");
try{
p.parse();}
catch (ErreurSyntax n){
retourEx=6;
}
assertEquals(retourEx,6);

}
public void testgetQuestionType() throws StringIndexOutOfBoundsException{
WikiReader p=new WikiReader();
QuestionType q=null;
q =p.getQuestionType("{C'est le type par défaut des questions d'un quiz. "
+ "Vous n'êtes pas obligé de terminer l'intitulé par :|type=\"[]\"}"
+ "\n+ Les lignes suivantes sont des propositions"
+ "+ justes ou"
+ "- fausses.");

assertEquals(q,QuestionType.MultipleChoice);
q =p.getQuestionType("{C'est le type par défaut des questions d'un quiz. "
+ "Vous n'êtes pas obligé de terminer l'intitulé par :|type=\"hhh\"}"
+ "\n+ Les lignes suivantes sont des propositions"
+ "+ justes ou"
+ "- fausses.");
assertEquals(q,QuestionType.Undefined);
}
}
