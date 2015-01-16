package io.vteial.wys.web.system;

import io.vteial.wys.model.Account
import io.vteial.wys.model.Address
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.model.Country
import io.vteial.wys.model.Customer
import io.vteial.wys.model.Item
import io.vteial.wys.model.User

println 'clear started...'

try {

	def entities = AutoNumber.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' autonNumbers deleted'

	entities = Country.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' countrys deleted'

	entities = Address.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' addresses deleted'

	entities = User.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' users deleted'

	entities = Customer.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' customers deleted'

	entities = Item.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' items deleted'

	entities = Account.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' accounts deleted'

}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'clear finished...'
