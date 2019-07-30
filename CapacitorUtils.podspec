
  Pod::Spec.new do |s|
    s.name = 'CapacitorUtils'
    s.version = '0.0.1'
    s.summary = 'Capacitor Plugins For Android'
    s.license = 'MIT'
    s.homepage = 'https://github.com/zhaohuaqiao/CapacitorUtils.git'
    s.author = 'zhaohuaqiao'
    s.source = { :git => 'https://github.com/zhaohuaqiao/CapacitorUtils.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end