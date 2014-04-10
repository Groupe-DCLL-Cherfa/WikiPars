package ParserGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import org.tsaap.questions.Question;
import org.tsaap.questions.Quiz;

/**
 * @author Cherfa
 */
@SuppressWarnings("serial")
public class Window extends JFrame {

	/**
	 * the number of rows for output box.
	 */
	private static final int INFO_NUM_ROW = 5;
	/**
	 * the number of columns for output box.
	 */
	private static final int INFO_NUM_COL = 20;
	/**
	 * Score percentage.
	 */
	private static final int SCORE_PERCENTAGE = 100;
	/**
	 * a quiz.
	 */
	private Quiz quiz;
	/**
	 * reset button.
	 */
	private JButton bReset;
	/**
	 * validation button.
	 */
	private JButton bValider;
	/**
	 * scroll panel.
	 */
	private JScrollPane jScrollPane1, resScroll;
	/**
	 * output information area.
	 */
	private JTextArea information;
	/**
	 * questions panel.
	 */
	private JPanel questionsPanel, buttonPanel;
	/**
	 * QuestionJPanel list.
	 */
	private List<QuestionJPanel> panelList = new ArrayList<QuestionJPanel>();

	/**
	 * Creates new form Window.
	 * 
	 * @param input
	 *            the quiz to set
	 */
	public Window(final Quiz input) {
		this.quiz = input;
		initComponents();
	}

	/**
	 * This method is called from within the initComponents method to set up the
	 * questions of quiz with their formats.
	 */
	private void intializeQuizView() {
		for (Question question : quiz.getQuestionList()) {
			QuestionJPanel panel = null;
			switch (question.getQuestionType()) {
			case MultipleChoice:
				panel = new MultipleChoicePanel(question);
				break;
			case ExclusiveChoice:
				panel = new ExclusiveChoicePanel(question);
				break;
			default:
				throw new RuntimeException("Not implemented");
			}
			panelList.add(panel);
			questionsPanel.add(panel);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		bValider = new JButton();
		bReset = new JButton();
		jScrollPane1 = new JScrollPane();
		resScroll = new JScrollPane();
		information = new JTextArea();
		questionsPanel = new JPanel();
		buttonPanel = new JPanel();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("******Wikiversity Parser******");
		bValider.setText("Valider");
		bReset.setText("Réinitialiser");
		information.setEditable(false);
		information.setBackground(new java.awt.Color(0, 0, 0));
		information.setColumns(INFO_NUM_COL);
		information.setForeground(Color.green);
		information.setRows(INFO_NUM_ROW);

		intializeQuizView();
		jScrollPane1.setViewportView(questionsPanel);
		resScroll.setViewportView(information);
		questionsPanel
				.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		questionsPanel.setSize(getPreferredSize());
		information.setSize(getPreferredSize());
		buttonPanel.add(bValider);
		buttonPanel.add(bReset);

		bValider.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				bValiderActionPerformed(evt);
			}
		});
		bReset.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				bResetActionPerformed(evt);
			}
		});
		BorderLayout layout = new BorderLayout();
		getContentPane().setLayout(layout);
		add(jScrollPane1, BorderLayout.NORTH);
		add(resScroll, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

	/**
	 * this method compute the score.
	 * @param evt
	 *            an event
	 */
	private void bValiderActionPerformed(final ActionEvent evt) {
		String resultat = "";
		int n = 1;
		for (QuestionJPanel question : panelList) {
			float score = question.compute() * SCORE_PERCENTAGE;
			resultat = resultat.concat("- Question ");
			Integer i;
			i =  Integer.valueOf(n);
			resultat = resultat.concat(i.toString());
			resultat = resultat.concat(" -> ");
			Double d = new Double(score);
			resultat = resultat.concat(d.toString());
			resultat = resultat.concat(" %\n");
			n++;
		}
		information.append(resultat);
	}

	/**
	 * this method reset the form.
	 * 
	 * @param evt
	 *            an event
	 */
	private void bResetActionPerformed(final ActionEvent evt) {
		for (QuestionJPanel panel : panelList) {
			panel.reset();
		}
		information.setText("");
	}
}
