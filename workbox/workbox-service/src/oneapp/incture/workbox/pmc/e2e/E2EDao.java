package oneapp.incture.workbox.pmc.e2e;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;

import oneapp.incture.workbox.util.ServicesUtil;

/**
 * @author Sai.Tharun
 *
 */
public class E2EDao {
	
	EntityManager em;
	
	int rectWidth = E2EProcessViewService.rectWidth;
	int rectHeight = E2EProcessViewService.rectHeight;

	public E2EDao(EntityManager em) {
		this.em = em;
	}

	public E2EDao() {
	}

	List<SlaDTO> slas = null;
	List<CoordinatesDTO> coords = null;
	
	
	/**
	 * @param processName
	 * @return List of mappings for a particular ProcessName
	 */
	@SuppressWarnings("unchecked")
	public List<MappingDTO> getMappings(String processName){
		List<MappingDTO> mapsList = null;
		
		String query = "select tsm.mappingId, tsm.slaId, tsm.nextTaskSlaId, tsm.rejectFlag from TaskMappingDo tsm where tsm.slaId in (select ts.slaId from SlaManagementDo ts where ts.processName = '"+processName+"')";
		Query queryForMappings = em.createQuery(query);
		
		List<Object[]> resultMappings = queryForMappings.getResultList();
		
		if(!ServicesUtil.isEmpty(resultMappings)){
			mapsList = new ArrayList<MappingDTO>();
			for(Object[] obj : resultMappings){
				MappingDTO mapdto = new MappingDTO();
				mapdto.setMappingId((String) obj[0]);
				mapdto.setCurrSla((String) obj[1]);
				mapdto.setNextSla((String) obj[2]);
				mapdto.setRejectFlag((String) obj[3]);
				mapsList.add(mapdto);
			}
		}
		return mapsList;
	}

	/**
	 * @param processName
	 * @return lane count for a particular ProcessName
	 */
	public Integer getLaneCount(String processName) {
		Integer laneCount = null;
		
		String query = "SELECT pc.laneCount FROM ProcessConfigDo pc WHERE pc.processName = '"+processName+"'";
		Query queryForLaneCount = em.createQuery(query);
		laneCount = (Integer) queryForLaneCount.getSingleResult();
		if (!ServicesUtil.isEmpty(laneCount)) {
			return laneCount;
		} else {
			return null;
		}
	}
	
	/**
	 * @param processName
	 * @return List of Sla Dtos for a ProcessName
	 */
	@SuppressWarnings("unchecked")
	public List<SlaDTO> getTaskDetails(String processName) {
		System.err.println("[pmc][tharun][in]");
		List<SlaDTO> listSlas = new ArrayList<SlaDTO>();
		try {
			String qry = "SELECT S.slaId, S.processName, S.taskName, S.modeName, S.lane FROM SlaManagementDo S WHERE S.processName = ?1";
			Query queryForSlas = em.createQuery(qry);

			queryForSlas.setParameter(1, processName);

			List<Object[]> resultList = queryForSlas.getResultList();
			System.err.println("[pmc][tharun]" + resultList);
			System.err.println("[pmc][tharun][in]");

			for (Object obj[] : resultList) {
				SlaDTO sladto = new SlaDTO();
				sladto.setTaskSlaId((String) obj[0]);
				sladto.setProcName((String) obj[1]);
				sladto.setTaskDef((String) obj[2]);
				sladto.setTaskMode((String) obj[3]);
				sladto.setLane((Integer) obj[4]);
				if(!getNextTaskSlaId(sladto.getTaskSlaId()).equals(null)){
					sladto.setNextTaskSlaId(getNextTaskSlaId(sladto.getTaskSlaId()));
				}
				listSlas.add(sladto);
			}

			return listSlas;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * @param taskSlaId
	 * @return Next Task SlaId for a particular taskSlaId
	 */
	@SuppressWarnings("unchecked")
	private List<String> getNextTaskSlaId(String taskSlaId) {
		List<String> taskSlas = new ArrayList<String>();
		try {
			String query = "SELECT T.nextTaskSlaId FROM TaskMappingDo T WHERE T.slaId = '" + taskSlaId + "'";
			Query queryForNextSlas = em.createQuery(query);

			List<String> resultObjects = queryForNextSlas.getResultList();
			for (String obj : resultObjects) {
				taskSlas.add(obj);
				System.err.println("[PMC][NEXT]" + (String) obj);
			}
			return taskSlas;
		} catch (Exception e) {
			System.err.println("getNextTaskSlaId - " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param laneCount
	 * @param slaDtos
	 * @return Map of Lane Objects which contain Sla id's according to Lane number
	 */
	public Map<Integer, List<SlaDTO>> createLaneObects(Integer laneCount, List<SlaDTO> slaDtos) {
		Map<Integer, List<SlaDTO>> laneStructureMap = new HashMap<Integer, List<SlaDTO>>();
		if (slaDtos != null && laneCount != null) {
			for (SlaDTO dto : slaDtos) {
				if (laneStructureMap.containsKey(dto.getLane())) {
					laneStructureMap.get(dto.getLane()).add(dto);
				} else {
					List<SlaDTO> dtos = new ArrayList<SlaDTO>();
					dtos.add(dto);
					laneStructureMap.put(dto.getLane(), dtos);
				}
			}
		}
		return laneStructureMap;
	}

	/**
	 * @param taskSlaId
	 * @param nextTaskSlaId
	 * @return if a Task Sla Id is accepted
	 */
	public boolean isAccepted(String taskSlaId, String nextTaskSlaId) {
		boolean ret = false;
		try {
			if(nextTaskSlaId != (null)){
				String qry = "SELECT T.rejectFlag FROM TaskMappingDo T WHERE T.slaId ='"+taskSlaId+"' AND T.nextTaskSlaId ='"+nextTaskSlaId+"'";
				Query query = em.createQuery(qry);

				String flag = (String) query.getSingleResult();
				if (flag != null) {
					if (flag.equalsIgnoreCase("Approved")) {
						ret = true;
					}
				} 
				return ret;
			} else {
				ret = true;
				return ret;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ret;
		}
	}

	/**
	 * @param processName
	 * @param newCoordDTOs
	 * @return List of rejected Tasks
	 */
	@SuppressWarnings("unchecked")
	public List<CoordinatesDTO> getRejectedTasks(String processName, List<CoordinatesDTO> newCoordDTOs) {
		List<CoordinatesDTO> list = null;
		String curTaskSla = null;
		String nextTaskSla = null;
		Dimension curDim = null;
		Dimension nexDim = null;

		try {
			
			String qry = "SELECT TM.slaId, TM.nextTaskSlaId FROM TaskMappingDo TM WHERE TM.slaId IN (SELECT SD.slaId FROM SlaManagementDo SD WHERE SD.processName ='"+processName+"') AND TM.rejectFlag = 'Rejected'";
			Query query = em.createQuery(qry);
			list = new ArrayList<CoordinatesDTO>();
			query.setParameter("procName", processName);

			List<Object[]> resultList = query.getResultList();
			if (ServicesUtil.isEmpty(resultList)) {
				for (Object[] obj : resultList) {
					curTaskSla = (String) obj[0];
					nextTaskSla = (String) obj[1];

					CoordinatesDTO cod = new CoordinatesDTO();
					for (CoordinatesDTO codto : newCoordDTOs) {

						if (curTaskSla.equals(codto.getTaskSlaId())) {
							curDim = new Dimension(codto.getxCoord(), codto.getyCoord());
						}
					}
					for (CoordinatesDTO codt : newCoordDTOs) {
						if (nextTaskSla.equals(codt.getTaskSlaId())) {
							nexDim = new Dimension(codt.getxCoord(), codt.getyCoord());
						}
					}

					cod.setTaskSlaId(curTaskSla);
					cod.setNextTaskSlaId(nextTaskSla);
					cod.setxCoord((int) curDim.getWidth());
					cod.setyCoord((int) curDim.getHeight());
					cod.setNextXCoord((int) nexDim.getWidth());
					cod.setNextYCoord((int) nexDim.getHeight());
					cod.setFlag('R');

					list.add(cod);

				}
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return list;
	}

	/**
	 * @param image
	 * @param type
	 * @return base 64 String of a BufferedImage
	 */
	public String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			Base64 base64 = new Base64();
			imageString = new String(base64.encode(imageBytes));
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}

	/**
	 * @param g2d
	 * @param processName
	 * @param newCoordDTOs
	 * @param slaDtos
	 * 
	 * This method is used to Draw connections between Tasks according to the Coordinates Plotted and Sla Dto's
	 */
	public void drawConnections(Graphics2D g2d, String processName, List<CoordinatesDTO> newCoordDTOs,
			List<SlaDTO> slaDtos) {

		Dimension currDim;
		Dimension nextDim;
		Dimension tempDim = null;

		slas = new ArrayList<SlaDTO>(slaDtos);
		coords = new ArrayList<CoordinatesDTO>(newCoordDTOs);

		E2EDao e2eDao = null;

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.BLACK);
		for (CoordinatesDTO co : newCoordDTOs) {
			if (!co.equals(null)) {

				e2eDao = new E2EDao();
				currDim = new Dimension(co.getxCoord(), co.getyCoord());
				nextDim = new Dimension(co.getNextXCoord(), co.getNextYCoord());

				if (co.getNextTaskSlaId() != (null)) {
					if (co.getFlag() == 'A') {
						if (currDim.getWidth() == nextDim.getWidth() && currDim.getHeight() > nextDim.getHeight()) {
							
							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)), (int) (currDim.getHeight()),
									(int) (nextDim.getWidth() + (rectWidth / 2)),
									(int) (nextDim.getHeight() + (rectHeight)));

							e2eDao.drawArrow((int) (currDim.getHeight()), (int) (nextDim.getWidth() + (rectWidth / 2)),
									(int) (nextDim.getHeight() + (rectHeight)), g2d);

						} else if (currDim.getWidth() == nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {
							
							System.err.println("[pmc][ids] : "+co.getTaskSlaId()+",  "+co.getNextTaskSlaId());
							
							if (isParNotLast(co.getTaskSlaId(), processName)) {

								String lastParTask = getParLast(co.getTaskSlaId());

								for (CoordinatesDTO cod : newCoordDTOs) {
									if (lastParTask.equals(cod.getTaskSlaId())) {
										tempDim = new Dimension(cod.getxCoord(), cod.getyCoord());
									}
								}
								System.err.println("[pmc][new][into this not par last]");
								g2d.setColor(Color.BLACK);
								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + (rectHeight)),
										(int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)),
										(int) (currDim.getWidth() + (rectWidth / 2) + rectWidth),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + rectWidth),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)),
										(int) (currDim.getWidth() + (rectWidth / 2) + rectWidth),
										(int) (tempDim.getHeight() + rectHeight + (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + rectWidth),
										(int) (tempDim.getHeight() + rectHeight + (rectHeight / 2)),
										(int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (tempDim.getHeight() + rectHeight + (rectHeight / 2)));

							} else if(isParLast(co.getNextTaskSlaId(), processName)){
								System.err.println("[pmc][isParNotFirst] : curr:"+co.getTaskSlaId()+",   next:"+co.getNextTaskSlaId());
								
								System.err.println("[pmc][isParNotFirst] : curr: "+currDim.getWidth()+", "+currDim.getHeight()+"next : "+nextDim.getWidth()+", "+nextDim.getHeight());
								
								g2d.setColor(Color.BLACK);
								//g2d.drawLine(x1, y1, x2, y2);
								
								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + (rectHeight)),
										(int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + rectHeight + (rectHeight / 2)),
										(int) (currDim.getWidth() - (rectWidth / 2)),
										(int) (currDim.getHeight() + (rectHeight + (rectHeight / 2))));

								g2d.drawLine((int) (currDim.getWidth() - (rectWidth / 2)),
										(int) (currDim.getHeight() + (rectHeight + (rectHeight / 2))),
										(int) (currDim.getWidth() - (rectWidth / 2)),
										(int) (nextDim.getHeight() - rectHeight + (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() - (rectWidth / 2)),
										(int) (nextDim.getHeight() - rectHeight + (rectHeight / 2)),
										(int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (nextDim.getHeight() - (rectHeight / 2)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (nextDim.getHeight() - (rectHeight / 2)),
										(int) (nextDim.getWidth() + (rectWidth / 2)), 
										(int) (nextDim.getHeight()));
								
								e2eDao.drawArrow((int) (nextDim.getHeight() - (rectHeight / 2)),
										(int) (nextDim.getWidth() + (rectWidth / 2)), 
										(int) (nextDim.getHeight()), g2d);
								
							} else {
								g2d.setColor(Color.BLACK);
								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2)),
										(int) (currDim.getHeight() + (rectHeight)),
										(int) (nextDim.getWidth() + (rectWidth / 2)), (int) (nextDim.getHeight()));

								e2eDao.drawArrow((int) (currDim.getHeight() + (rectHeight)),
										(int) (nextDim.getWidth() + (rectWidth / 2)), (int) (nextDim.getHeight()), g2d);
								
								
								System.err.println("currTask :"+co.getTaskSlaId()+",  nextTask :"+co.getNextTaskSlaId());
							}

						} else if (currDim.getHeight() == nextDim.getHeight()
								&& currDim.getWidth() < nextDim.getWidth()) {

							System.err.println("[pmc][into left to right]");
							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) - 20),
									(int) (currDim.getHeight() + (rectHeight / 2)), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							e2eDao.drawSideArrow((int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2)), g2d);

						} else if (currDim.getHeight() == nextDim.getHeight()
								&& currDim.getWidth() > nextDim.getWidth()) {
							
							System.err.println("[pmc][into right to left]");
							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth()), (int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)));
							
							e2eDao.drawSideArrow((int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)), g2d);

						} else if (currDim.getWidth() < nextDim.getWidth()
								&& currDim.getHeight() > nextDim.getHeight()) {

							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth() + rectWidth),
									(int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() - (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (nextDim.getWidth() - (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (nextDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							e2eDao.drawSideArrow((int) (nextDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2)), g2d);

						} else if (currDim.getWidth() < nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) - 20),
									(int) (currDim.getHeight() + (rectHeight / 2) + 7),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)) + 7);

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 7),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							e2eDao.drawSideArrow((int) (nextDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2)), g2d);

						} else if (currDim.getWidth() > nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth()), (int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (currDim.getWidth() - rectWidth),
									(int) (currDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (currDim.getWidth() - rectWidth),
									(int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (currDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (currDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() + rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							e2eDao.drawSideArrow((int) (nextDim.getHeight() + (rectHeight / 2)),
									(int) (nextDim.getWidth() + rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2)), g2d);

						} else if (currDim.getWidth() > nextDim.getWidth()
								&& currDim.getHeight() > nextDim.getHeight()) {

							g2d.setColor(Color.BLACK);
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2)));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)));

							e2eDao.drawSideArrow((int) (currDim.getHeight() + (rectHeight / 2)),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2)), g2d);

						}
					} else if (co.getFlag() == 'R') {
						if (currDim.getWidth() == nextDim.getWidth() && currDim.getHeight() > nextDim.getHeight()) {

							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + 10), (int) (currDim.getHeight()),
									(int) (nextDim.getWidth() + (rectWidth / 2) + 10),
									(int) (nextDim.getHeight() + (rectHeight)));

							e2eDao.drawArrow((int) (currDim.getHeight()), (int) (nextDim.getWidth() + (rectWidth / 2) + 10),
									(int) (nextDim.getHeight() + (rectHeight)), g2d);

						} else if (currDim.getWidth() == nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {

							if (isParNotLast(co.getTaskSlaId(), processName)) {

								String lastParTask = getParLast(co.getTaskSlaId());

								for (CoordinatesDTO cod : newCoordDTOs) {
									if (lastParTask.equals(cod.getTaskSlaId())) {
										tempDim = new Dimension(cod.getxCoord(), cod.getyCoord());
									}
								}

								g2d.setColor(Color.RED.brighter());
								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + 10),
										(int) (currDim.getHeight() + (rectHeight)),
										(int) (currDim.getWidth() + (rectWidth / 2) + 10),
										(int) (currDim.getHeight() + rectHeight + (rectHeight)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + 10),
										(int) (currDim.getHeight() + rectHeight + (rectHeight)),
										(int) (currDim.getWidth() + (rectWidth / 2) + rectWidth + 10),
										(int) (currDim.getHeight() + rectHeight + (rectHeight)));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + rectWidth + 10),
										(int) (currDim.getHeight() + rectHeight + (rectHeight)),
										(int) (currDim.getWidth() + (rectWidth / 2) + rectWidth + 10),
										(int) (tempDim.getHeight() + rectHeight + rectHeight));

								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + rectWidth + 10),
										(int) (tempDim.getHeight() + rectHeight + rectHeight),
										(int) (currDim.getWidth() + (rectWidth / 2) + 10),
										(int) (tempDim.getHeight() + rectHeight + rectHeight));

							} else {
								g2d.setColor(Color.RED.brighter());
								g2d.drawLine((int) (currDim.getWidth() + (rectWidth / 2) + 10),
										(int) (currDim.getHeight() + (rectHeight)),
										(int) (nextDim.getWidth() + (rectWidth / 2) + 10), (int) (nextDim.getHeight()));

								e2eDao.drawArrow((int) (currDim.getHeight() + (rectHeight)),
										(int) (nextDim.getWidth() + (rectWidth / 2) + 10), (int) (nextDim.getHeight()), g2d);

							}

						} else if (currDim.getHeight() == nextDim.getHeight()
								&& currDim.getWidth() < nextDim.getWidth()) {

							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							e2eDao.drawSideArrow((int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2) + 10), g2d);

						} else if (currDim.getHeight() == nextDim.getHeight()
								&& currDim.getWidth() > nextDim.getWidth()) {

							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth()), (int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));
							
							e2eDao.drawLeftSideArrow((int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10), g2d);

						} else if (currDim.getWidth() < nextDim.getWidth()
								&& currDim.getHeight() > nextDim.getHeight()) {

							System.err.println("into 1");
							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth() + rectWidth),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() - (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (nextDim.getWidth() - (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (nextDim.getWidth() - rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							e2eDao.drawSideArrow((int) (nextDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2) + 10), g2d);

						} else if (currDim.getWidth() < nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {
							System.err.println("into 2");
							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							e2eDao.drawSideArrow((int) (nextDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth()), (int) (nextDim.getHeight() + (rectHeight / 2) + 10), g2d);

						} else if (currDim.getWidth() > nextDim.getWidth()
								&& currDim.getHeight() < nextDim.getHeight()) {
							System.err.println("into 3");
							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth()), (int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() - rectWidth + 10),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() - rectWidth + 10),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() - rectWidth + 10),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() - rectWidth + 10),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() + rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							e2eDao.drawLeftSideArrow((int) (nextDim.getHeight() + (rectHeight / 2) + 10),
									(int) (nextDim.getWidth() + rectWidth),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10), g2d);

						} else if (currDim.getWidth() > nextDim.getWidth()
								&& currDim.getHeight() > nextDim.getHeight()) {

							System.err.println("into 4");
							g2d.setColor(Color.RED.brighter());
							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10), (int) (nextDim.getWidth()),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (nextDim.getHeight() + (rectHeight / 2) + 10));

							g2d.drawLine((int) (currDim.getWidth() + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10));

							e2eDao.drawSideArrow((int) (currDim.getHeight() + (rectHeight / 2) + 10),
									(int) (currDim.getWidth() + (rectWidth) + (rectWidth)),
									(int) (currDim.getHeight() + (rectHeight / 2) + 10), g2d);

						}
					}
				}
			}
		}
	}

	/**
	 * @param taskSlaId
	 * @param processName
	 * @return if a Task is parallel and Last task
	 */
	private boolean isParLast(String taskSlaId, String processName) {
		System.err.println("[pmc][into][isParNotFirst] :"+taskSlaId);
		List<String> parSlas = null;

		System.err.println("[pmc][into][isParNotFirst]");
		System.err.println("[pmc][isParNotFirst][processName] : " + processName);
		
		for (SlaDTO sla : slas) {
			if (sla.getNextTaskSlaId().size() > 1 && sla.getNextTaskSlaId().contains(taskSlaId)) {
				parSlas = new ArrayList<String>();
				parSlas = sla.getNextTaskSlaId();
				System.err.println("[pmc][ids][par_ids] : "+parSlas);
				Map<String, Integer> ycoordMap = new HashMap<String, Integer>();
				ValueComparator vc = new ValueComparator(ycoordMap);
				TreeMap<String, Integer> sortYCoordMap = new TreeMap<String, Integer>(vc);
				for (String str : parSlas) {
					for (CoordinatesDTO co : coords) {
						if (co.getTaskSlaId().equals(str)) {
							ycoordMap.put(str, co.getyCoord());
						}
					}
				}
				sortYCoordMap.putAll(ycoordMap);
				System.err.println("[pmc][ids][last_key] : "+sortYCoordMap.firstKey());
				if (taskSlaId.equals(sortYCoordMap.firstKey())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param taskSlaId
	 * @param processName
	 * @return if a Task is parallel but not last task
	 */
	private boolean isParNotLast(String taskSlaId, String processName) {
		List<String> parSlas = null;
		
		System.err.println("[pmc][into][isParNotLast]");
		System.err.println("[pmc][isParNotLast][processName] : "+processName);
		for (SlaDTO sla : slas) {
			if (sla.getNextTaskSlaId().size() > 1 && sla.getNextTaskSlaId().contains(taskSlaId)
					&& isPar(taskSlaId, processName)) {
				parSlas = new ArrayList<String>();
				parSlas = sla.getNextTaskSlaId();
				Map<String, Integer> ycoordMap = new HashMap<String, Integer>();
				ValueComparator vc = new ValueComparator(ycoordMap);
				TreeMap<String, Integer> sortYCoordMap = new TreeMap<String, Integer>(vc);
				for (String str : parSlas) {
					for (CoordinatesDTO co : coords) {
						if (co.getTaskSlaId().equals(str)) {
							ycoordMap.put(str, co.getyCoord());
						}
					}
				}
				sortYCoordMap.putAll(ycoordMap);
				if (taskSlaId.equals(sortYCoordMap.firstKey())) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	class ValueComparator implements Comparator<String> {
		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	/**
	 * @param taskSlaId
	 * @param processName
	 * @return if a task is parallel task
	 */
	@SuppressWarnings("unchecked")
	private boolean isPar(String taskSlaId, String processName) {
		List<String> listParSlas = null;
		boolean ret = false;

		try {
			System.err.println("[pmc][into][isPar] " + taskSlaId +", "+processName);
			//String qry = "SELECT TD.slaId FROM TaskMappingDo TD WHERE TD.nextTaskSlaId IN (SELECT TM.nextTaskSlaId FROM TaskMappingDo TM, SlaManagementDo SM WHERE TM.slaId = SM.slaId AND SM.processName ='"+processName+"' GROUP BY (TM.nextTaskSlaId) HAVING COUNT(*) > 1) GROUP BY (TD.slaId)";
			String qry = "SELECT TD.TASK_SLA_ID " +
					"FROM TASK_STEP_MAPPING TD " +
					"WHERE TD.NEXT_TASK_ID IN " +
					"  (SELECT TM.NEXT_TASK_ID " +
					"  FROM TASK_STEP_MAPPING TM, " +
					"    TASK_SLA SM " +
					"  WHERE TM.TASK_SLA_ID = SM.TASK_SLA_ID " +
					"  AND SM.PROC_NAME     ='"+processName+"' " +
					"  AND TM.REJECT_FLAG <> 'Rejected'" +
					"  GROUP BY (TM.NEXT_TASK_ID) " +
					"  HAVING COUNT(*) > 1 " +
					"  ) " +
					"GROUP BY (TD.TASK_SLA_ID)";
			System.err.println("[pmc][parallel_tasks][query] :"+qry);
			Query query = em.createNativeQuery(qry, "parallelTaskResults");
			listParSlas = new ArrayList<String>();
			List<Object> resultSet = query.getResultList();

			for (Object obj : resultSet) {
				listParSlas.add((String) obj);
			}
			for(String str : listParSlas){
				System.err.println("[pmc][e2e][parTasks] "+str);
			}
			if(listParSlas.contains(taskSlaId)){
				ret = true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ret;
	}

	/**
	 * @param taskSlaId
	 * @return Last parallel task for a Sla Id
	 */
	private String getParLast(String taskSlaId) {
		List<String> parSlas = null;
		for (SlaDTO sla : slas) {
			if (sla.getNextTaskSlaId().size() > 1 && sla.getNextTaskSlaId().contains(taskSlaId)) {
				parSlas = new ArrayList<String>();
				parSlas = sla.getNextTaskSlaId();
				Map<String, Integer> ycoordMap = new HashMap<String, Integer>();
				ValueComparator vc = new ValueComparator(ycoordMap);
				TreeMap<String, Integer> sortYCoordMap = new TreeMap<String, Integer>(vc);
				for (String str : parSlas) {
					for (CoordinatesDTO co : coords) {
						if (co.getTaskSlaId().equals(str)) {
							ycoordMap.put(str, co.getyCoord());
						}
					}
				}
				sortYCoordMap.putAll(ycoordMap);
				return sortYCoordMap.firstKey();
			}
		}
		return null;
	}

	/**
	 * @param k
	 * @param i
	 * @param j
	 * @param g2d
	 * 
	 * draws and Arrow, normal side
	 */
	public void drawArrow(int k, int i, int j, Graphics2D g2d) {
		if (k < j) {
			g2d.drawLine(i + 5, j - 5, i, j);
			g2d.drawLine(i - 5, j - 5, i, j);
		} else if (k > j) {
			g2d.drawLine(i - 5, j + 5, i, j);
			g2d.drawLine(i + 5, j + 5, i, j);
		}
	}

	/**
	 * @param k
	 * @param i
	 * @param j
	 * @param g2d
	 * 
	 * draws and side Arrow 
	 */
	public void drawSideArrow(int k, int i, int j, Graphics2D g2d) {
		g2d.drawLine(i - 5, j - 5, i, j);
		g2d.drawLine(i - 5, j + 5, i, j);
	}

	
	/**
	 * @param i
	 * @param j
	 * @param k
	 * @param g2d
	 * 
	 *  draw LeftSideArrow
	 */
	public void drawLeftSideArrow(int i, int j, int k, Graphics2D g2d) {
		g2d.drawLine(j + 5, k - 5, j, k);
		g2d.drawLine(j + 5, k + 5, j, k);
	}

	/**
	 * @param g2d
	 * @param processName
	 * 
	 * draw Lane Headers
	 */
	public void drawLaneHeaders(Graphics2D g2d, String processName) {
		
		g2d.setColor(new Color(255, 0, 0));
		g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		g2d.drawString("Requester", 140, 75);
		g2d.drawString("Approver", 480, 75);
		g2d.drawString("Reviewer", 820, 75);
		
	}
	
}
