package com.ericsson.edr.discoveryadapter.common.dto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonDTO {

	private static final String DELIMITTER_REGEX = "\\.";

	private static final String DELIMITTER = ".";

	
	private String id;
	
	@JsonIgnore
	private String name;
	
	@JsonIgnore	
	private Map<String, List<Object>> aggregatedObjects = new HashMap<>();
	
	public List<Object> getAggregatedObject(String key) {
		return aggregatedObjects.get(key);
	}
	
	public Map<String, List<Object>> getAggregatedObjects() {
		return this.aggregatedObjects;
	}

	protected void addAggregatedObject(String key, Object aggregatedObject) {
		List<Object> objects = this.aggregatedObjects.computeIfAbsent(key, f -> new ArrayList<>());
		objects.add(aggregatedObject);
	}
	/**
	 * to store all attributes of the object
	 */
	
	protected Map<String, Object> attributes;
	
	interface EnumAttribute{
		
	}
	
	public enum ContainerAttributes implements EnumAttribute{
		FIBER_CABLE_COUNT("container_Attributes.fiber_Cable_Count"), PARENT_DEPTH("dimensions.parent_Depth"), DISTANCE_FROM_FRONT("dimensions.distance_From_Front"), CUSTOM_ATT_2("container_Attributes.custom_ATT_2"), WIDTH("dimensions.width"), OPTICAL_NE("container_Attributes.optical_NE"), HEIGHT("dimensions.height"), EQUIPMENT_POSITION_Y_OFFSET("orientation.equipment_Position_Y_Offset"), DISTANCE_FROM_BOTTOM("dimensions.distance_From_Bottom"), SOFTWARE_REVISION("primary.software_Revision"), TYPE("primary.type"), CUSTOMER("associated_Object_Attributes.customer"), CLEI("location.clei"), DUE("dates.due"), CUSTOM_ATT_1("container_Attributes.custom_ATT_1"), EQUIPMENT_POSITION_X_OFFSET("orientation.equipment_Position_X_Offset"), PARENT_WIDTH("dimensions.parent_Width"), NAME("primary.name"), VENDOR("primary.vendor"), SERIAL_NUMBER("asset_Information.serial_Number"), GATEWAY_TYPE("container_Attributes.gateway_Type"), UNIT_OF_MEASURE("additional_Fields.unit_Of_Measure"), CLLI("location.clli"), SOFTWARE_VERSION("container_Attributes.software_Version"), PATCH_VERSION_LIST("container_Attributes.patch_Version_List"), PARENT_HEIGHT("dimensions.parent_Height"), TG_MEM_ACTIVE("interfaces.tg_Mem_Active"), DEVICE_ID("interfaces.device_ID"), LOOPBACK_ADDRESS("connectionless_Svc_Attributes.loopback_Address"), PARENT_SITE_CLLI("associated_Object_Attributes.parent_Site_CLLI"), SUBNET_PATH("container_Attributes.subnet_Path"), PARENT_CONTAINER_NAME("associated_Object_Attributes.parent_Container_Name"), EQUIPMENT_POSITION_Z_OFFSET("orientation.equipment_Position_Z_Offset"), SUBNET("container_Attributes.subnet"), NE_ID("container_Attributes.ne_ID"), DEPTH("dimensions.depth"), ORDERED("dates.ordered"), COMMENTS("comments.comments"), EQUIPMENT_ROLE("connectionless_Svc_Attributes.equipment_Role"), DEPRECIATION_YEARS_("asset_Information.depreciation_Years_"), LSR_ID("container_Attributes.lsr_ID"), SUBNET_MASK("container_Attributes.subnet_Mask"), INSTALLED("dates.installed"), LOOPBACK_INTERFACE_NAME("connectionless_Svc_Attributes.loopback_Interface_Name"), DECOMMISSION("dates.decommission"), LINEUP("location.lineup"), DISTANCE_FROM_LEFT("dimensions.distance_From_Left"), MANAGEMENT("container_Attributes.management"), BATCH_NUMBER("asset_Information.batch_Number"), ALIAS("container_Attributes.alias"), DIMENSION_UNITS("dimensions.dimension_Units"), AID_FORMULA("additional_Fields.aid_Formula"), NE_MAC_ADDRESS("container_Attributes.ne_MAC_Address"), MODIFY_DOMAIN_GROUP("primary.modify_Domain_Group"), BAR_CODE("asset_Information.bar_Code"), REMARKS("container_Attributes.remarks"), PURCHASE_PRICE("asset_Information.purchase_Price"), FRAME("location.frame"), PARENT_SITE_NAME("associated_Object_Attributes.parent_Site_Name"), SCHEDULED("dates.scheduled"), STATUS("primary.status"), MODEL("primary.model"), ORDER_NO("asset_Information.order_No"), RUNNING_STATUS("container_Attributes.running_Status"), GATEWAY("container_Attributes.gateway"), CONTACTS("container_Attributes.contacts"), HECIG("additional_Fields.hecig"), PURCHASE_DATE("asset_Information.purchase_Date"), NE_IP_ADDRESS("container_Attributes.ne_IP_Address"), NMS_EMS("interfaces.nms_EMS"), NPA("switch_Trunk_Information.npa"), QUERY_DOMAIN_GROUP("primary.query_Domain_Group"), ROTATION("orientation.rotation"), IN_SERVICE("dates.in_Service"), NETWORK_ID("primary.network_ID");
		String value;
		ContainerAttributes(String value){
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		addAttribute("id", id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String addAttribute(EnumAttribute EnumAttribute, Object value) {
		return addAttribute(EnumAttribute.toString(), value);
	}
	/**
	 * to add any attribute to the object
	 * @param key in the format groupName.attributeName
	 * @param value attribute value as String
	 * @return value if set successfully
	 */
	@SuppressWarnings("unchecked")
	@JsonAnySetter
	public String addAttribute(String key, Object value) {
		if(! (value instanceof String)){
			return null;
		}
		if(attributes == null){
			attributes = new HashMap<>();
		}
		if (key.contains(DELIMITTER)){ /* to handle group attributes */
			String[] split = key.split(DELIMITTER_REGEX);

			String groupName = split[0];
			String attributeName = split[1];
			Object groupObject = attributes.get(groupName); /* getting attribute map for individual group */
			Map<String,String> groupAttributeMap;			
			if(groupObject == null){
				groupAttributeMap = new HashMap<>();	
				attributes.put(groupName, groupAttributeMap); /* adding the group to the main map */
			} else {
				groupAttributeMap = (Map<String, String>) groupObject;
			}
			return groupAttributeMap.put(attributeName, (String) value); /* adding an attribute to the group */
		} else {
			return (String) attributes.put(key, value); /* setting normal attributes */
		}
	}
	
	/**
	 * to get any attribute value of the object
	 * @param key as attribute name in the format groupName.attributeName
	 * @return attribute value as String
	 */
	@SuppressWarnings("unchecked")
	public String getAttribute(String key) {
		if(attributes == null){
			return null;
		}
		if (key.contains(DELIMITTER)){ /* to handle group attributes */
			String[] split = key.split(DELIMITTER_REGEX);

			String groupName = split[0];
			String attributeName = split[1];
			Object groupObject = attributes.get(groupName); /* getting attribute map for individual group */
			Map<String,String> groupAttributeMap;			
			if(groupObject == null){
				return null; /* not attribute for this group is set */
			} else {
				groupAttributeMap = (Map<String, String>) groupObject;
				return groupAttributeMap.get(attributeName); /* returning attribute value from the group */
			}			
		} else {
			return (String) attributes.get(key); /* getting normal attribute value */
		}
	}
	
    @JsonAnyGetter
    protected Map<String, Object> getMap() {
      return attributes;
    }
	/**
	 * Generic method to generate XSD type objects from map of attributes
	 * @param clazz The class type of object to be created
	 * @param dto The DTO with attribute map. The map keys should be same as attribute names in xsd
	 * @return an object of type {@linkplain clazz} with attributes populated as present in the {@linkplain dto}
	 */
	public <T> T createTypeObject(Class<T> clazz) {	
	    
		// Create ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();
		
		//map commonDTO to xsd type object
		Object object = mapper.convertValue(this.attributes, clazz);
	
		return (clazz).cast(object);
	}
}