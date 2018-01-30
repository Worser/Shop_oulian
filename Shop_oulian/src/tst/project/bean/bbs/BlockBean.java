package tst.project.bean.bbs;

import java.util.List;

import tst.project.utils.StringHandler;

public class BlockBean {
	private int id;							//版块ID值
	private int categoryId;					//存储版块所属类别的ID值
	private String blockName;				//版块名称
	private int blockId;
	private String blockInfo;				//版块描述
	private String blockStatus;				//版块状态：2-开放；1-锁定；0-关闭
	private int blockOrder;					//版块的排列次序
	private String blockFoundTime;			//版块创建时间
	private TopicBean lastTopic;			//版块中最后发表的主题
	private int blockAllTopicNum;			//版块中主题数
	private int blockBestTopicNum;			//版块中精华帖子数
	private List masters;					//版块的所有版主名称
	private String is_delete;				//板块是否删除 
	public int getId() {
		return id;
	}
	public BlockBean setId(int id) {
		this.id = id;
		return this;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public BlockBean setCategoryId(int categoryId) {
		this.categoryId = categoryId;
		return this;
	}
	public String getBlockName() {
		return blockName;
	}
	public BlockBean setBlockName(String blockName) {
		this.blockName = blockName;
		return this;
	}
	public int getBlockId() {
		return blockId;
	}
	public BlockBean setBlockId(int blockId) {
		this.blockId = blockId;
		return this;
	}
	public String getBlockInfo() {
		return blockInfo;
	}
	public BlockBean setBlockInfo(String blockInfo) {
		this.blockInfo = blockInfo;
		return this;
	}
	public String getBlockStatus() {
		return blockStatus;
	}
	public BlockBean setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
		return this;
	}
	public int getBlockOrder() {
		return blockOrder;
	}
	public BlockBean setBlockOrder(int blockOrder) {
		this.blockOrder = blockOrder;
		return this;
	}
	public String getBlockFoundTime() {
		return blockFoundTime;
	}
	public BlockBean setBlockFoundTime(String blockFoundTime) {
		this.blockFoundTime = blockFoundTime;
		return this;
	}
	public TopicBean getLastTopic() {
		return lastTopic;
	}
	public BlockBean setLastTopic(TopicBean lastTopic) {
		this.lastTopic = lastTopic;
		return this;
	}
	public int getBlockAllTopicNum() {
		return blockAllTopicNum;
	}
	public BlockBean setBlockAllTopicNum(int blockAllTopicNum) {
		this.blockAllTopicNum = blockAllTopicNum;
		return this;
	}
	public int getBlockBestTopicNum() {
		return blockBestTopicNum;
	}
	public BlockBean setBlockBestTopicNum(int blockBestTopicNum) {
		this.blockBestTopicNum = blockBestTopicNum;
		return this;
	}
	public List getMasters() {
		return masters;
	}
	public BlockBean setMasters(List masters) {
		this.masters = masters;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public BlockBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	
}
