/**
 *
 *描述:货源所属人
 * @author liyixing
 * @version 1.0
 * @since 2015年9月7日 上午10:47:24
 */

package cn.gou23.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gou23.shop.dao.SourceOwnerEntityMapper;
import cn.gou23.shop.entity.SourceOwnerEntityCondition;
import cn.gou23.shop.model.SourceOwnerModel;
import cn.gou23.shop.service.SourceOwnerService;

/**
 * 
 * 描述:
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年9月7日 上午10:47:24
 */
@Service
@Transactional
public class SourceOwnerServiceImpl implements SourceOwnerService {
	@Autowired
	private SourceOwnerEntityMapper sourceOwnerEntityMapper;

	/**
	 * @see cn.gou23.shop.service.SourceOwnerService#getByOwnerAndType(cn.gou23.shop.model.SourceOwnerModel)
	 */
	@Override
	public SourceOwnerModel getByOwnerAndType(SourceOwnerModel sourceOwnerModel) {
		SourceOwnerEntityCondition sourceOwnerEntityCondition = new SourceOwnerEntityCondition();

		sourceOwnerEntityCondition.createCriteria()
				.andTypeEqualTo(sourceOwnerModel.getType())
				.andSourceOwnerEqualTo(sourceOwnerModel.getSourceOwner());

		List<SourceOwnerModel> sourceOwnerModels = sourceOwnerEntityMapper
				.selectByExampleWithBLOBs(sourceOwnerEntityCondition);
		
		return sourceOwnerModels.size() > 0 ? sourceOwnerModels.get(0) : null;
	}

	/**
	 * @see cn.gou23.shop.service.SourceOwnerService#save(cn.gou23.shop.model.SourceOwnerModel)
	 */

	@Override
	public void save(SourceOwnerModel sourceOwnerModel) {
		SourceOwnerModel oldSourceOwnerModel = getByOwnerAndType(sourceOwnerModel);

		if (oldSourceOwnerModel != null) {
			oldSourceOwnerModel.setRemark(sourceOwnerModel.getRemark());
			sourceOwnerEntityMapper
					.updateByPrimaryKeyWithBLOBs(oldSourceOwnerModel);
		} else {
			sourceOwnerEntityMapper.insert(sourceOwnerModel);
		}
	}

}
