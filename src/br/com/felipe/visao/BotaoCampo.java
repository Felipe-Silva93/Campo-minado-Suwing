package br.com.felipe.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


import br.com.felipe.modelo.Campo;
import br.com.felipe.modelo.CampoEvento;
import br.com.felipe.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{
	//cor dos potoes e elementtos de tela
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCADO = new Color(8, 179, 247);
	private final Color BG_EXPLOSAO = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);

	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		campo.registrarObservador(this);
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));//da um efeuto visual na bordar
		
		campo.registrarObservador(this);
		addMouseListener(this);// a pripria classe adiciona o evento do mouse e sera notificada a cada evento do mouse
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch(evento) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			apliicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			aplicarEstiloPadrao();
			
		
		}
		SwingUtilities.invokeLater(()->{//revalida o componente garente que o botão seja 100% renderizado forçando que o mesmo seja desenhado na tela revalidano e printado na tela
			repaint();
			validate();
		});
	
	}
	

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLOSAO);
		setForeground(Color.WHITE);
		setText("X");

	}

	private void apliicarEstiloMarcar() {
		setBackground(BG_MARCADO);
		setForeground(Color.BLACK);

		setText("M");

	}

	private void aplicarEstiloAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLOSAO);
			return;
		}
		
		setBackground(BG_PADRAO);

		switch(campo.minasNaVizinhanca()) {
		
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;	
		case 4:
		case 5:
		case 6:	
			setForeground(Color.RED);
			break;	
		default:
			setForeground(Color.PINK);

			
		}
		String valor = !campo.vizinhancaSegura()? campo.minasNaVizinhanca()+"":"";
		setText(valor);
	}

	//Interface dos eventos do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() ==1) {
			campo.abrir();
		}else {
			campo.alterarMarcacao();
		}
		
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
}
