import io.vteial.wys.model.Employee


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	Employee employee = Employee.get('munmin2000@maxmoney')

	println employee
}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''