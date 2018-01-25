package oneapp.incture.workbox.pmc.e2e;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import oneapp.incture.workbox.pmc.dto.ResponseMessage;
import oneapp.incture.workbox.pmc.services.EntityManagerProviderLocal;
import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Sai.Tharun
 *
 */
@Stateless
public class E2EProcessViewService implements E2EProcessViewServiceLocal {

	@EJB
	EntityManagerProviderLocal entityManager;

	public E2EProcessViewService() {
	}
	
	static final int rectHeight = 60, rectWidth = 90;
	
	/* (non-Javadoc)
	 * @see com.incture.pmc.e2e.services.E2EProcessViewServiceLocal#drawImage(java.lang.String)
	 * 
	 * Drawing Naive Level BPM Flow for a particular processName for which details are fetched from the DB
	 * 
	 */
	@Override
	public E2EProcessResponse drawImage(String processName) {
		
		
		final int startEndWidth = 45, startEndHeight = 45;

		List<CoordinatesDTO> coordDTOs = null;
		List<CoordinatesDTO> newCoordDTOs = null;
		List<SlaDTO> slaDtos = null;
		List<CoordinatesDTO> rejectedTasks = null;
		
		E2EDao e2eDao = new E2EDao(entityManager.getEntityManager());
		
		E2EProcessResponse response = new E2EProcessResponse();
		
		final int imgWidth = 1020;
		final int imgHeight = 700;
		final int arcWidth = 5;
		final int arcHeight = 6;

		System.err.println("[pmc][tharun][in] :" + processName);

		Integer laneCount = e2eDao.getLaneCount(processName);
		System.err.println("[pmc][e2e][laneCount] "+laneCount);
		if(!ServicesUtil.isEmpty(laneCount)){
			slaDtos = new ArrayList<SlaDTO>();
			slaDtos = e2eDao.getTaskDetails(processName);
			
			Comparator<SlaDTO> sortBySlaId = new Comparator<SlaDTO>() {

				@Override
				public int compare(SlaDTO o1, SlaDTO o2) {
					return extractInt(o1.getTaskSlaId()) - extractInt(o2.getTaskSlaId());
				}
				int extractInt(String s) {
			        String num = s.replaceAll("\\D", "");
			        return num.isEmpty() ? 0 : Integer.parseInt(num);
			    }
			};
			
			Collections.sort(slaDtos, sortBySlaId);
			for(SlaDTO dto : slaDtos){
				System.err.println("[pmc][e2e][taskDtos] "+dto.toString());
			}
		}else {
			ResponseMessage respMessage = new ResponseMessage();
			respMessage.setMessage("Base 64 Not Sent, No Lane Number Found! - " +processName);
			respMessage.setStatus("Failure");
			respMessage.setStatusCode("0");
			response.setResponseMessage(respMessage);
		}

		if(!slaDtos.equals(null) && !ServicesUtil.isEmpty(slaDtos)){
			
			Map<Integer, List<SlaDTO>> laneStructureMap = e2eDao.createLaneObects(laneCount, slaDtos);
			
			for(Integer key : laneStructureMap.keySet()){
				System.err.println("[pmc][e2e][map] "+key +" Value : "+laneStructureMap.get(key));
			}

			BufferedImage buffImage = new BufferedImage(imgWidth - 8, imgHeight, BufferedImage.TYPE_INT_RGB);

			try {

				Graphics2D g2d = buffImage.createGraphics();
				//g2d.setPaint(Color.WHITE.brighter());
				g2d.setPaint(new Color(224,224,224));
				g2d.fillRect(0, 0, buffImage.getWidth(), buffImage.getHeight());

				int linesY = 0;
				int linesX = 0;

				for (int i = 0; i < imgWidth; i = i + 20) {
					// g2d.drawLine(0, i, imgWidth - 40, i);
					linesX++;
				}

				for (int j1 = 0; j1 <= imgWidth; j1 = j1 + 20) {
					// g2d.drawLine(j1, 0, j1, imgWidth - 40);
					linesY++;
				}

				double laneSize = 0.0;
				try {
					laneSize = (double) linesY / laneCount;
				} catch (ArithmeticException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}

				g2d.drawLine(0, 90, 1020, 90);
				g2d.setColor(Color.WHITE.brighter());
				g2d.fillRect(0, 45, buffImage.getWidth(), 45);
				
				int i_laneSize = (int) laneSize;
				int draw_line = i_laneSize * 20;
				for (int x = 0; x <= imgWidth; x = x + draw_line) {
					g2d.setStroke(new BasicStroke(1));
					g2d.setColor(new Color(255,0,0));
					g2d.setStroke(
							new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0));
					
					if(x != imgWidth){
						g2d.drawLine(x, 0, x, imgWidth);
					} else {
						g2d.drawLine(x-10, 0, x-10, imgWidth);
					}
					
				}

				/*g2d.setColor(Color.RED);
				g2d.drawLine(0, 45, 1020, 45);
				g2d.setColor(Color.WHITE.brighter());
				g2d.fillRect(0, 0, buffImage.getWidth(), 45);
				g2d.setColor(Color.RED);
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				g2d.drawString(processName + " Process", 420, 30);
				g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));*/
				
				coordDTOs = new ArrayList<CoordinatesDTO>();

				for (int i = 1; i <= laneCount; i++) {

					double laneHeight = 0.0;

					try {
						laneHeight = (double) linesX / (laneStructureMap.get(i).size() + 1);
					} catch (ArithmeticException e) {
						System.err.println("ArithmeticException" + e.getMessage());
					}

					int i_laneHeight = (int) laneHeight;
					i_laneHeight = i_laneHeight - 3;

					int cnt = 1;

					for (SlaDTO sla : laneStructureMap.get(i)) {
						int lane = i;
						int lSize = (imgWidth / laneCount) - 10;

						g2d.setStroke(new BasicStroke(4));

						String st = sla.getTaskDef();

						if (st.equalsIgnoreCase("Start")) {
							g2d.setColor(Color.GREEN);
							g2d.drawRoundRect((lSize * lane) - 180 + 20 + 3, (i_laneHeight * 20 * cnt) + 20 - 5, startEndWidth,
									startEndHeight, arcWidth + 35, arcHeight + 35);
							//g2d.setColor(new Color(224,224,224));
							g2d.setColor(Color.WHITE.brighter());
							g2d.fillRoundRect((lSize * lane) - 180 + 20 + 3, (i_laneHeight * 20 * cnt) + 20 - 5, startEndWidth,
									startEndHeight, arcWidth + 35, arcHeight + 35);
						} else if (st.equalsIgnoreCase("End")) {
							g2d.setColor(new Color(255,0,2));
							g2d.drawRoundRect((lSize * lane) - 180 + 20 + 2, (i_laneHeight * 20 * cnt), startEndWidth,
									startEndHeight, arcWidth + 35, arcHeight + 35);
							//g2d.setColor(new Color(224,224,224));
							g2d.setColor(Color.WHITE.brighter());
							g2d.fillRoundRect((lSize * lane) - 180 + 20 + 2, (i_laneHeight * 20 * cnt), startEndWidth,
									startEndHeight, arcWidth + 35, arcHeight + 35);
						} else {
							g2d.setColor(Color.BLUE);
							g2d.drawRoundRect((lSize * lane) - 180, (i_laneHeight * 20 * cnt), rectWidth, rectHeight,
									arcWidth, arcHeight);
							//g2d.setColor(new Color(224,224,224));
							g2d.setColor(Color.WHITE.brighter());
							g2d.fillRoundRect((lSize * lane) - 180, (i_laneHeight * 20 * cnt), rectWidth, rectHeight,
									arcWidth, arcHeight);
						}

						g2d.setColor(Color.BLACK.darker());
						g2d.setStroke(new BasicStroke(2));
						/*Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
						attributes.put(TextAttribute.FAMILY, "Open Sans ");
						attributes.put(TextAttribute.WEIGHT, Float.valueOf(1.95f));
						//attributes.put(TextAttribute.SIZE, 13);*/
						
						Font ttfBase = null;
						Font ttfReal = null;
						
						try {
							InputStream iStream = new BufferedInputStream(new FileInputStream("OpenSans-Regular.ttf"));
							ttfBase = Font.createFont(Font.TRUETYPE_FONT, iStream);
							ttfReal = ttfBase.deriveFont(Font.PLAIN, 13);
						} catch(Exception e) {
							e.printStackTrace();
						}
						
						String[] str = sla.getTaskDef().split(" ");
						int m = 0;
						for (String strn : str) {
							if (strn.equalsIgnoreCase("START")) {
								//g2d.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 9));
								//attributes.put(TextAttribute.SIZE, 10);
								//g2d.setFont(Font.getFont(attributes));
								g2d.setFont(ttfReal);
								g2d.drawString(strn, (lSize * lane) - 148, (i_laneHeight * 20 * cnt) + 43 + m);
								m = m + 12;
							} else if (strn.equalsIgnoreCase("END")) {
								//g2d.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 9));
								//attributes.put(TextAttribute.SIZE, 13);
								//g2d.setFont(Font.getFont(attributes));
								g2d.setFont(ttfReal);
								g2d.drawString(strn, (lSize * lane) - 147, (i_laneHeight * 20 * cnt) + 27 + m);
								m = m + 12;
							} else {
								//g2d.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 13));
								//attributes.put(TextAttribute.SIZE, 13);
								//g2d.setFont(Font.getFont(attributes));
								g2d.setFont(ttfReal);
								g2d.drawString(strn, (lSize * lane) - 175, (i_laneHeight * 20 * cnt) + 20 + m);
								m = m + 12;
							}
						}
						
						CoordinatesDTO coordDTO = new CoordinatesDTO();
						coordDTO.setxCoord((lSize * lane) - 180);
						coordDTO.setyCoord((i_laneHeight * 20 * cnt));
						coordDTO.setTaskSlaId(sla.getTaskSlaId());
						coordDTOs.add(coordDTO);
						cnt++;

					}
				}
				
				e2eDao.drawLaneHeaders(g2d, processName);
				
				newCoordDTOs = new ArrayList<CoordinatesDTO>();
				
				for(SlaDTO sla : slaDtos){
					System.err.println("[pmc][err][sla] : "+sla.toString());
				}

				List<MappingDTO> mappings = new ArrayList<MappingDTO>();
				
				mappings = e2eDao.getMappings(processName);
				
				for(MappingDTO maps : mappings){
					CoordinatesDTO cod = new CoordinatesDTO();
					for(CoordinatesDTO codto : coordDTOs){
						if(maps.getCurrSla().equals(codto.getTaskSlaId())){
							cod.setTaskSlaId(maps.getCurrSla());
							cod.setxCoord(codto.getxCoord());
							cod.setyCoord(codto.getyCoord());
							cod.setNextTaskSlaId(maps.getNextSla());
							for(CoordinatesDTO codn : coordDTOs){
								
								if(maps.getNextSla() != null){
									if(maps.getNextSla().equals(codn.getTaskSlaId())){
										cod.setNextXCoord(codn.getxCoord());
										cod.setNextYCoord(codn.getyCoord());
									}
								}
							}
							if(maps.getRejectFlag() != null){
								if(maps.getRejectFlag().equals("Approved")){
									cod.setFlag('A');
								} else if(maps.getRejectFlag().equals("Rejected")){
									cod.setFlag('R');
								}
							}
						}
					}
					newCoordDTOs.add(cod);
				}
				
				for(CoordinatesDTO co : newCoordDTOs){
					System.err.println("[pmc][coord][fin] :"+co.toString());
				}
				
				rejectedTasks = e2eDao.getRejectedTasks(processName, newCoordDTOs);

				newCoordDTOs.addAll(rejectedTasks);
				
				e2eDao.drawConnections(g2d, processName, newCoordDTOs, slaDtos);
				
				BufferedImage croppedImage = buffImage.getSubimage(0, 45, buffImage.getWidth()-6, buffImage.getHeight()-45);
				
				//String retBase64String = e2eDao.encodeToString(buffImage, "jpg");
				String retBase64String = e2eDao.encodeToString(croppedImage, "jpg");
				
				System.err.println("[PMC][FIN][STR] : "+retBase64String);
				
				response.setBase64String(retBase64String);
				ResponseMessage respMessage = new ResponseMessage();
				respMessage.setMessage("Base 64 Sent");
				respMessage.setStatus("Success");
				respMessage.setStatusCode("1");
				response.setResponseMessage(respMessage);
				
			} catch (Exception e) {
				ResponseMessage respMessage = new ResponseMessage();
				respMessage.setMessage("Base 64 Not Sent, Exception : "+e.getMessage());
				respMessage.setStatus("Failure");
				respMessage.setStatusCode("0");
				response.setResponseMessage(respMessage);
			}
		} else {
			ResponseMessage respMessage = new ResponseMessage();
			respMessage.setMessage("Base 64 Not Sent, Process Name not Found! - " +processName);
			respMessage.setStatus("Failure");
			respMessage.setStatusCode("0");
			response.setResponseMessage(respMessage);
		}
		return response;
	}
}
