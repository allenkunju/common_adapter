package com.ericsson.edr.discoveryadapter.common.enumgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

public class EnumClassGenerator {
	private static Logger LOG = Logger.getLogger(EnumClassGenerator.class);

	private static String LIST_TYPE = "List";
	private static String OBJECT_TYPE = "Object";
	private static String ENUM_TYPE = "Enum";
	private static String REFERENCE_TYPE = "Reference";
	private Map<String,Integer> attrMap;
	
	private Map<String,Boolean> startObjects = new HashMap<>();
	
	private Map<String, Map<String, Set<String>>> typeAttributeMap = new HashMap<>();
	
//	private Map<String,Set<String>> typeEnumMap = new HashMap<>();

	public void generateEnumClasses(String rootClass, String location, String packageVal){
		Class<?> root = null;
		try {
			root = Class.forName(rootClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LOG.error(String.format("Class %s could not be loaded",rootClass));
			System.exit(0);
		}
		
		processValidObjectType(root);
		
		writeDTOClasses(root,location, packageVal);
		writeDataCollector(root,location, packageVal);
		writeDataProcessor(root,location, packageVal);
		writeResponseBuilder(root,location, packageVal);
		writeHandler(root,location, packageVal);
	}
	
	private void writeHandler(Class<?> root, String location, String packageVal) {
		String fileLocation = location + packageVal.replace('.', '/') + "/handler/";
		mkDirs(fileLocation); // create directories if doesn't exist

		String RootObjectName = root.getSimpleName();
		String fileName = fileLocation + RootObjectName + "Handler.java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+packageVal+".handler;\n");
		sb.append("\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.Adapter;\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.handler.AdapterHandler;\n");
		sb.append("import "+packageVal+".datacollector."+RootObjectName+"DataCollector;\n");
		sb.append("import "+packageVal+".dataprocessor."+RootObjectName+"DataProcessor;\n");
		sb.append("import "+packageVal+".responsebuilder."+RootObjectName+"ResponseBuilder;\n");
		sb.append("import com.telcordia.discoveryadapter.common.DiscoveryException;\n");
		sb.append("import com.telcordia.discoveryadapter.common.Request;\n");
		sb.append("\n");
		sb.append("public class "+RootObjectName+"Handler extends AdapterHandler {\n");
		sb.append("	\n");
		sb.append("	@Override\n");
		sb.append("	protected Adapter getAdapter(Request arg0) throws DiscoveryException {\n");
		sb.append("		return new Adapter(new "+RootObjectName+"DataCollector(),"
				+ " new "+RootObjectName+"DataProcessor(), new "+RootObjectName+"ResponseBuilder(\""+RootObjectName+"\"));\n");
		sb.append("	}\n");
		sb.append("	\n");
		sb.append("	@Override\n");
		sb.append("	public String getType() {\n");
		sb.append("		return  null;\n");
		sb.append("	}\n");
		sb.append("}\n");
		
		try(FileWriter fw = new FileWriter(fileName);) {
			fw.write(sb.toString());
		} catch (IOException ex) {
			System.out.println("EnumClassGenerator - Exception detected: " + ex.getMessage());
			ex.printStackTrace(System.out);
		}
	}
	
	private void writeDataCollector(Class<?> root, String location, String packageVal) {
		
		String fileLocation = location + packageVal.replace('.', '/') + "/datacollector/";
		mkDirs(fileLocation); // create directories if doesn't exist

		String RootObjectName = root.getSimpleName();
		String fileName = fileLocation + RootObjectName + "DataCollector.java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+packageVal+".datacollector;\n");
		sb.append("\n");
		sb.append("import com.telcordia.discoveryadapter.common.Request;\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.DataCollector;\n");
		sb.append("import com.telcordia.discoveryadapter.common.DiscoveryException;\n");
		sb.append("\n");
		sb.append("public final class "+RootObjectName+"DataCollector implements DataCollector {\n");
		sb.append("	@Override\n");
		sb.append("	public Object collectData(Request arg0) throws DiscoveryException {\n");
		sb.append("		//TODO write logic to collect the NMS Data and return it to DataProcessor in any object format\n");
		sb.append("		return null;\n");
		sb.append("	}\n");
		sb.append("}\n");
		
		try(FileWriter fw = new FileWriter(fileName);) {
			fw.write(sb.toString());
		} catch (IOException ex) {
			System.out.println("EnumClassGenerator - Exception detected: " + ex.getMessage());
			ex.printStackTrace(System.out);
		}
	
	}

	private void writeDataProcessor(Class<?> root, String location, String packageVal) {
		
		String fileLocation = location + packageVal.replace('.', '/') + "/dataprocessor/";
		mkDirs(fileLocation); // create directories if doesn't exist

		String RootObjectName = root.getSimpleName();
		String fileName = fileLocation + RootObjectName + "DataProcessor.java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+packageVal+".dataprocessor;\n");
		sb.append("\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.DataProcessor;\n");
		sb.append("import com.telcordia.discoveryadapter.common.DiscoveryException;\n");
		sb.append("\n");
		sb.append("public final class "+RootObjectName+"DataProcessor implements DataProcessor {\n");
		sb.append("	@Override\n");
		sb.append("	public CommonDTO processData(Object arg0) throws DiscoveryException {\n");
		sb.append("		//TODO generate return start object\n");
		sb.append("		return null;\n");
		sb.append("	}\n");
		sb.append("}\n");
		
		try(FileWriter fw = new FileWriter(fileName);) {
			fw.write(sb.toString());
		} catch (IOException ex) {
			System.out.println("EnumClassGenerator - Exception detected: " + ex.getMessage());
			ex.printStackTrace(System.out);
		}
	
	}

	private void writeResponseBuilder(Class<?> root, String location, String packageVal) {
		
		String fileLocation = location + packageVal.replace('.', '/') + "/responsebuilder/";
		mkDirs(fileLocation); // create directories if doesn't exist

		String RootObjectName = root.getSimpleName();
		String fileName = fileLocation + RootObjectName + "ResponseBuilder.java";
		StringBuilder sb = new StringBuilder();
		sb.append("package "+packageVal+".responsebuilder;\n");
		sb.append("\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;\n");
		sb.append("import java.lang.reflect.Method;\n");
		sb.append("import java.util.List;\n");
		sb.append("import java.util.Map.Entry;\n");
		sb.append("import com.ericsson.edr.discoveryadapter.common.responsebuilder.VoResponseBuilder;\n");
		sb.append("import "+packageVal+"."+RootObjectName+".dto.*;\n");
		sb.append("import "+root.getPackage().getName()+".*;\n");
		sb.append("import "+root.getName()+".*;\n");
		sb.append("\n");
		sb.append("public final class "+RootObjectName+"ResponseBuilder extends VoResponseBuilder {\n");
		sb.append("	public "+RootObjectName+"ResponseBuilder(String name) {\n");
		sb.append("		super(name);\n");
		sb.append("	}\n");
		sb.append("	\n");
		sb.append("	@Override\n");
		sb.append("	protected Object generateRootObject(CommonDTO startObject) {\n");
		sb.append("		"+RootObjectName+" root = new "+RootObjectName+"();\n");
		sb.append("		"+RootObjectName+"_StartObjects startObjects = new "+RootObjectName+"_StartObjects();\n");
		sb.append("		"+RootObjectName+"_AggregatedObjects aggregatedObjects = new "+RootObjectName+"_AggregatedObjects();\n");
		sb.append("		root.set"+RootObjectName+"_StartObjects(startObjects);\n");
		sb.append("		root.set"+RootObjectName+"_AggregatedObjects(aggregatedObjects);\n");
		sb.append("		\n");
		
		for (Entry<String, Boolean> entry : startObjects.entrySet()) {
			String objectType = entry.getKey();
			String startObjectName = objectType.replace("Type", "");
			Boolean isList = entry.getValue();
			sb.append("		if(startObject instanceof "+startObjectName+"DTO){\n");
			if(isList){
				sb.append("			startObjects.get"+startObjectName+"().add(startObject.createTypeObject("+startObjectName+"Type.class));\n");
			}else{
				sb.append("			startObjects.set"+startObjectName+"(startObject.createTypeObject("+startObjectName+"Type.class));\n");
			}
			sb.append("			for (Entry<String, List<Object>> entry : startObject.getAggregatedObjects().entrySet()) {\n");
			sb.append("				String key = entry.getKey();\n");
			sb.append("				startObject.getAggregatedObject(key).stream().forEach(item -> {\n");
			sb.append("					try{\n");
			sb.append("						Method getMethod = aggregatedObjects.getClass().getDeclaredMethod(\"get\"+key);\n");
			sb.append("						List list = (List) getMethod.invoke(aggregatedObjects);\n");
			sb.append("						list.add(item);\n");
			sb.append("					}catch(Exception e){\n");
			sb.append("						//logger\n");
			sb.append("					}\n");
			sb.append("					});\n");
			sb.append("			}\n");
			sb.append("		}\n");
			
		}
		sb.append("		return root;\n");
		sb.append("	}\n");
		sb.append("}\n");
		
		try(FileWriter fw = new FileWriter(fileName);) {
			fw.write(sb.toString());
		} catch (IOException ex) {
			System.out.println("EnumClassGenerator - Exception detected: " + ex.getMessage());
			ex.printStackTrace(System.out);
		}
	
	}

	private void writeDTOClasses(Class<?> root, String location, String packageVal) {
		String rootObjectName = root.getSimpleName();

		for (Entry<String, Map<String, Set<String>>> entry : typeAttributeMap.entrySet()) {
			String objectType = entry.getKey(); // ContainerType
			String objectName = objectType.replaceAll("Type", "");
			Map<String, Set<String>> attributeMap = entry.getValue();
			
			String javaCode = generateEnumClass(packageVal +"."+ rootObjectName + ".dto", objectName, attributeMap, root);
			
			String fileLocation = location + packageVal.replace('.', '/') + "/" +rootObjectName+"/dto/";
			mkDirs(fileLocation); // create directories if doesn't exist

			String fileName = fileLocation + objectName + "DTO.java";
			try(FileWriter fw = new FileWriter(fileName);) {
				fw.write(javaCode);
			} catch (IOException ex) {
				System.out.println("EnumClassGenerator - Exception detected: " + ex.getMessage());
				ex.printStackTrace(System.out);
			}
		}
	}

	private void mkDirs(String fileLocation) {
		File directory = new File(fileLocation);
		if (!directory.exists()) {
		    
		    System.out.println("Directory not exists, creating now");

		    boolean success = directory.mkdirs();
		    if (success) {
		        System.out.printf("Successfully created new directory : %s%n", fileLocation);
		    } else {
		        System.out.printf("Failed to create new directory: %s%n", fileLocation);
		    }
		}
	}

	private void processValidObjectType(Class<?> objectType) {
		XmlType xmlTypeAnnotation = objectType.getAnnotation(XmlType.class);
		if(xmlTypeAnnotation == null){
			return; //skip if jaxb class is not @XmlType
		}
		String xmlTypeAnnotationName = xmlTypeAnnotation.name(); //if empty then not a required type
		if(xmlTypeAnnotationName == null || xmlTypeAnnotationName.isEmpty()){	
			String className = objectType.getSimpleName();
			
			Field[] fields = objectType.getDeclaredFields();
			for (Field childElement : fields) {
				Class<?> type = childElement.getType();
				if(type == List.class){
					Type subEnumType = childElement.getGenericType();
					Class<?> clazz = (Class<?>) ((ParameterizedType) subEnumType).getActualTypeArguments()[0];
					processValidObjectType(clazz);			
					if(className.endsWith("StartObjects")){
						startObjects.put(clazz.getSimpleName(),true);
					}
				}else {
					if(className.endsWith("StartObjects")){
						startObjects.put(type.getSimpleName(),false);
					}
					processValidObjectType(type);					
				}
			}							
		} else {
			if(typeAttributeMap.containsKey(xmlTypeAnnotationName)){
				return;				
			}
			attrMap = new HashMap<>();
			populateEnumMapGroup(objectType, xmlTypeAnnotationName);
		}
	}

	private void populateEnumMapGroup(Class<?> objectType, String enumTypeName) {
		
		Field[] fields = objectType.getDeclaredFields();
		for (Field groupElement : fields) {
			XmlElement xmlElement = groupElement.getAnnotation(XmlElement.class);
			if(xmlElement == null){
				continue;
			}
			String groupName = groupElement.getName();
			Class<?> type = groupElement.getType();
			Map<String, Set<String>> attributeMap = typeAttributeMap.computeIfAbsent(enumTypeName, f -> new HashMap<>());

			if(type == List.class){
				Class<?> clazz = (Class<?>) ((ParameterizedType) groupElement.getGenericType()).getActualTypeArguments()[0];
				
				String simpleName = clazz.getSimpleName();
				if("Object".equals(simpleName)){
					continue;
				}
				if(clazz.getSimpleName().endsWith("Type")){
					attributeMap.computeIfAbsent(LIST_TYPE, f -> new HashSet<>()).add(simpleName);
					processValidObjectType(clazz);
				} else {
					attributeMap.computeIfAbsent(REFERENCE_TYPE, f -> new HashSet<>()).add(clazz.getSimpleName());
				}
			} else {
				XmlType xmlTypeAnnotation = type.getAnnotation(XmlType.class);
				if(xmlTypeAnnotation == null || !xmlTypeAnnotation.name().isEmpty()){
					Set<String> objectAttrSet = attributeMap.computeIfAbsent(OBJECT_TYPE, f -> new HashSet<>());
					objectAttrSet.add(type.getSimpleName());
					processValidObjectType(type); //skip if jaxb class is not @XmlType. if not empty then not a required group
				} else {
					
					populateEnumMapAttributes(type, groupName, attributeMap);
				}
			}
		}
	}

	private void populateEnumMapAttributes(Class<?> groupType, String groupName, Map<String, Set<String>> attributeMap) {
		Field[] attrTypes = groupType.getDeclaredFields();
		for (Field attr : attrTypes) {
			XmlElement xmlElement = attr.getAnnotation(XmlElement.class);
			if(xmlElement == null){
				continue;
			}
			String attrName = attr.getName();
			String enumValue = generateEnumValue(groupName, attrName);
			Set<String> attrSet = attributeMap.computeIfAbsent(ENUM_TYPE, f -> new HashSet<>());
			attrSet.add(enumValue);
		}
		
	}

	private String generateEnumClass(String packageName, String objectName, Map<String, Set<String>> attributeMap, Class<?> root) {
		StringBuilder sb = new StringBuilder();

		sb.append("package "+packageName+";\n\n")
		  .append("import java.util.List;\n")
		  .append("import java.util.ArrayList;\n")
		  .append("import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;\n")
		  .append("import "+root.getPackage().getName()+".*;\n\n");
		sb.append("public class " + objectName + "DTO extends CommonDTO{\n\n");
		
		Set<String> listAttrs = attributeMap.get(LIST_TYPE);		
		if(listAttrs != null){
			for (Iterator iterator = listAttrs.iterator(); iterator.hasNext();) {
				String listAttrTypeName = (String) iterator.next();
				String listAttrName = listAttrTypeName.replaceAll("Type", "");
				sb.append("\tprivate List<"+listAttrName+"DTO> "+listAttrName+";\n\n");

				sb.append("\tpublic List<"+listAttrName+"DTO> get"+listAttrName+"() {\n");
				sb.append("\t	return this."+listAttrName+";\n");
				sb.append("\t}\n\n");
				
				sb.append("\tpublic void add"+listAttrName+"("+listAttrName+"DTO "+listAttrName+"){\n");
				sb.append("\t	if(this."+listAttrName+" == null){\n");
				sb.append("\t		this."+listAttrName+" = new ArrayList<>();\n");
				sb.append("\t	}\n");
				sb.append("\t	this."+listAttrName+".add("+listAttrName+");\n");
				sb.append("\t}\n\n");
			}
		}
		Set<String> objectAttrs = attributeMap.get(OBJECT_TYPE);
		if(objectAttrs != null){
			for (Iterator iterator = objectAttrs.iterator(); iterator.hasNext();) {
				String objectAttrTypeName = (String) iterator.next();
				String objectAttrName = objectAttrTypeName.replaceAll("Type", "");
				sb.append("\tprivate "+objectAttrName+"DTO "+objectAttrName+";\n\n");

				sb.append("\tpublic "+objectAttrName+"DTO get"+objectAttrName+"() {\n");
				sb.append("\t	return this."+objectAttrName+";\n");
				sb.append("\t}\n\n");
				
				sb.append("\tpublic void set"+objectAttrName+"("+objectAttrName+"DTO "+objectAttrName+"){\n");
				sb.append("\t	this."+objectAttrName+" = "+objectAttrName+";\n");
				sb.append("\t}\n\n");
			}
		}
		Set<String> refAttrs = attributeMap.get(REFERENCE_TYPE);		
		if(refAttrs != null){
			for (Iterator iterator = refAttrs.iterator(); iterator.hasNext();) {
				String refAttrTypeName = (String) iterator.next();
				String refAttrName = refAttrTypeName.replaceAll("Type", "");
				sb.append("\tprivate List<"+refAttrName+"DTO> "+refAttrName+";\n\n");

				sb.append("\tpublic List<"+refAttrName+"DTO> get"+refAttrName+"() {\n");
				sb.append("\t	return this."+refAttrName+";\n");
				sb.append("\t}\n\n");
				
				sb.append("\tpublic void add"+refAttrName+"("+refAttrName+"DTO "+refAttrName+"){\n");
				sb.append("\t	if(this."+refAttrName+" == null){\n");
				sb.append("\t		this."+refAttrName+" = new ArrayList<>();\n");
				sb.append("\t	}\n");
				sb.append("\t	this."+refAttrName+".add("+refAttrName+");\n");
				sb.append("\t}\n\n");
			}
		}
		Set<String> enumAttrs = attributeMap.get(ENUM_TYPE);
		if(enumAttrs != null){
			sb.append("\tpublic enum " + objectName + "AttributeKey {\n\n");
			sb.append("\t	");
			for (Iterator iterator = enumAttrs.iterator(); iterator.hasNext();) {
				String enumAttrName = (String) iterator.next();	
				sb.append(enumAttrName + ", ");
			}
			sb.replace(sb.lastIndexOf(","), sb.length(), ";\n\n");
			
			sb.append("\t	String value;\n\n");
			sb.append("\t	"+ objectName + "AttributeKey(String value){\n");
			sb.append("\t		this.value = value;\n");
			sb.append("\t	}\n\n");
			sb.append("\t	@Override\n");
			sb.append("\t	public String toString() {\n");
			sb.append("\t		return this.value;\n");
			sb.append("\t	}\n");
			sb.append("\t}\n");	
			
			sb.append("\tpublic String addAttribute("+ objectName + "AttributeKey key, Object value) {\n");
			sb.append("\t	return addAttribute(key.toString(), value);\n");
			sb.append("\t}\n");
		}
		
		
		if(refAttrs != null || refAttrs != null || objectAttrs != null){
			sb.append("\t@Override\n");
			sb.append("\tpublic <T> T createTypeObject(Class<T> clazz) {\n");
			sb.append("\n");
			sb.append("\t	T object = super.createTypeObject(clazz);\n");
			if(refAttrs != null){
				for (String refAttr : refAttrs) {
					sb.append("\t	if("+refAttr+" != null){ \n");
					sb.append("\t	"+refAttr+".stream().forEach((listItem)-> { \n");
					sb.append("\t		"+refAttr+"Type refObject = listItem.createTypeObject("+refAttr+"Type.class);\n");
					sb.append("\t		" + objectName + "Type."+refAttr+" reference = new " + objectName + "Type."+refAttr+"();\n");
					sb.append("\t		reference.setIDREF(refObject);\n");
					sb.append("\t		((" + objectName + "Type)object).get"+refAttr+"().add(reference );\n");
					sb.append("\n");
					sb.append("\t		addAggregatedObject(\""+refAttr+"\",refObject);\n");
					sb.append("\t	});\n");
					sb.append("\t	}\n");
					sb.append("\t\n");
				}
			}
			
			if(listAttrs != null){
				for (String listAttr : listAttrs) {
					String listAttrName = listAttr.replaceAll("Type", "");
					sb.append("\t	if("+listAttrName+" != null){ \n");
					sb.append("\t	"+listAttrName+".stream().forEach((listItem)-> { \n");
					sb.append("\t		((" + objectName + "Type)object).get"+listAttrName+"().add(listItem.createTypeObject("+listAttrName+"Type.class));\n");
					sb.append("\t		getAggregatedObjects().putAll(listItem.getAggregatedObjects());\n");
					sb.append("\t	});\n");
					sb.append("\t	}\n");
					sb.append("\t\n");
				}
			}

			if(objectAttrs != null){
				for (String ojbAttr : objectAttrs) {
					String objAttrName = ojbAttr.replaceAll("Type", "");
					sb.append("\t	if("+objAttrName+" != null){ \n");
					sb.append("\t	((" + objectName + "Type)object).set"+objAttrName+"("+objAttrName+".createTypeObject("+objAttrName+"Type.class));\n");
					sb.append("\t	}\n");
					sb.append("\n");
				}
			}
			
			sb.append("\t	return object;\n");
			sb.append("\t}\n");	
		}
		
		sb.append("}");
			
		return sb.toString();
	}

	private String generateEnumValue(String groupName, String attrName) {
		String enumName = attrName.toUpperCase();
		Integer occurance = attrMap.get(enumName);
		if(occurance == null){
			attrMap.put(enumName, 0);
		} else {
			occurance += 1;
			attrMap.put(enumName, occurance);
			enumName = enumName + occurance;
		}
		
		return enumName+"(\""+groupName+"."+attrName+"\")";
	}

	public static void main(String[] args) {
		EnumClassGenerator classGen = new EnumClassGenerator();
		if (args.length != 3) {
			System.out.println("Usage:  EnumClassGenerator <source jaxb root class name> <dest source location> <dest main package>\n"
					+ "eg: EnumClassGenerator com.telcordia.xdsc.dds.eh.EH src/ com.ericsson.edr.testadapter");
			System.exit(1);
		}
		classGen.generateEnumClasses(args[0],args[1], args[2]);
		
	}
}
