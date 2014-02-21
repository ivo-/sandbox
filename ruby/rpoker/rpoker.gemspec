# -*- encoding: utf-8 -*-
$:.push File.expand_path("../lib", __FILE__)
require "rpoker/version"

Gem::Specification.new do |s|
  s.name        = 'rpoker'
  s.version     = Rpoker::VERSION
  s.summary     = "Holdem for nerds."
  s.description = "Texas Holdem implementation in ruby. It is a good foundation for other poker or card games."
  s.authors     = ["Ivailo Hristov"]
  s.licenses    = ["MIT"]
  s.email       = 'ivailo.nikolaev.hristov@gmail.com'
  s.homepage    = 'https://github.com/ivo-/Rpoker'

  s.files         = `git ls-files`.split("\n")
  s.test_files    = `git ls-files -- {test,spec,features}/*`.split("\n")
  s.executables   = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }

  s.require_paths = ["./lib"]
end
