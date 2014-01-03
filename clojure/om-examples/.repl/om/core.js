// Compiled by ClojureScript 0.0-2138
goog.provide('om.core');
goog.require('cljs.core');
goog.require('om.dom');
goog.require('om.dom');
om.core._STAR_read_enabled_STAR_ = false;
om.core.IInitState = (function (){var obj9249 = {};return obj9249;
})();
om.core.init_state = (function init_state(this$){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IInitState$init_state$arity$1;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IInitState$init_state$arity$1(this$);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.init_state[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.init_state["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IInitState.init-state",this$);
}
}
})().call(null,this$);
}
});
om.core.IShouldUpdate = (function (){var obj9251 = {};return obj9251;
})();
om.core.should_update = (function should_update(this$,next_props,next_state){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IShouldUpdate$should_update$arity$3;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IShouldUpdate$should_update$arity$3(this$,next_props,next_state);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.should_update[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.should_update["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IShouldUpdate.should-update",this$);
}
}
})().call(null,this$,next_props,next_state);
}
});
om.core.IWillMount = (function (){var obj9253 = {};return obj9253;
})();
om.core.will_mount = (function will_mount(this$){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IWillMount$will_mount$arity$1;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IWillMount$will_mount$arity$1(this$);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.will_mount[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.will_mount["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IWillMount.will-mount",this$);
}
}
})().call(null,this$);
}
});
om.core.IDidMount = (function (){var obj9255 = {};return obj9255;
})();
om.core.did_mount = (function did_mount(this$,node){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IDidMount$did_mount$arity$2;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IDidMount$did_mount$arity$2(this$,node);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.did_mount[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.did_mount["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IDidMount.did-mount",this$);
}
}
})().call(null,this$,node);
}
});
om.core.IWillUnmount = (function (){var obj9257 = {};return obj9257;
})();
om.core.will_unmount = (function will_unmount(this$){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IWillUnmount$will_unmount$arity$1;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IWillUnmount$will_unmount$arity$1(this$);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.will_unmount[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.will_unmount["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IWillUnmount.will-unmount",this$);
}
}
})().call(null,this$);
}
});
om.core.IWillUpdate = (function (){var obj9259 = {};return obj9259;
})();
om.core.will_update = (function will_update(this$,next_props,next_state){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IWillUpdate$will_update$arity$3;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IWillUpdate$will_update$arity$3(this$,next_props,next_state);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.will_update[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.will_update["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IWillUpdate.will-update",this$);
}
}
})().call(null,this$,next_props,next_state);
}
});
om.core.IDidUpdate = (function (){var obj9261 = {};return obj9261;
})();
om.core.did_update = (function did_update(this$,prev_props,prev_state,root_node){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IDidUpdate$did_update$arity$4;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IDidUpdate$did_update$arity$4(this$,prev_props,prev_state,root_node);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.did_update[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.did_update["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IDidUpdate.did-update",this$);
}
}
})().call(null,this$,prev_props,prev_state,root_node);
}
});
om.core.IRender = (function (){var obj9263 = {};return obj9263;
})();
om.core.render = (function render(this$){if((function (){var and__4856__auto__ = this$;if(and__4856__auto__)
{return this$.om$core$IRender$render$arity$1;
} else
{return and__4856__auto__;
}
})())
{return this$.om$core$IRender$render$arity$1(this$);
} else
{var x__5489__auto__ = (((this$ == null))?null:this$);return (function (){var or__4868__auto__ = (om.core.render[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core.render["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IRender.render",this$);
}
}
})().call(null,this$);
}
});
om.core.ICursor = (function (){var obj9265 = {};return obj9265;
})();
om.core._value = (function _value(cursor){if((function (){var and__4856__auto__ = cursor;if(and__4856__auto__)
{return cursor.om$core$ICursor$_value$arity$1;
} else
{return and__4856__auto__;
}
})())
{return cursor.om$core$ICursor$_value$arity$1(cursor);
} else
{var x__5489__auto__ = (((cursor == null))?null:cursor);return (function (){var or__4868__auto__ = (om.core._value[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core._value["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"ICursor.-value",cursor);
}
}
})().call(null,cursor);
}
});
om.core._path = (function _path(cursor){if((function (){var and__4856__auto__ = cursor;if(and__4856__auto__)
{return cursor.om$core$ICursor$_path$arity$1;
} else
{return and__4856__auto__;
}
})())
{return cursor.om$core$ICursor$_path$arity$1(cursor);
} else
{var x__5489__auto__ = (((cursor == null))?null:cursor);return (function (){var or__4868__auto__ = (om.core._path[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core._path["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"ICursor.-path",cursor);
}
}
})().call(null,cursor);
}
});
om.core._state = (function _state(cursor){if((function (){var and__4856__auto__ = cursor;if(and__4856__auto__)
{return cursor.om$core$ICursor$_state$arity$1;
} else
{return and__4856__auto__;
}
})())
{return cursor.om$core$ICursor$_state$arity$1(cursor);
} else
{var x__5489__auto__ = (((cursor == null))?null:cursor);return (function (){var or__4868__auto__ = (om.core._state[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core._state["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"ICursor.-state",cursor);
}
}
})().call(null,cursor);
}
});
om.core.IToCursor = (function (){var obj9267 = {};return obj9267;
})();
om.core._to_cursor = (function() {
var _to_cursor = null;
var _to_cursor__2 = (function (value,state){if((function (){var and__4856__auto__ = value;if(and__4856__auto__)
{return value.om$core$IToCursor$_to_cursor$arity$2;
} else
{return and__4856__auto__;
}
})())
{return value.om$core$IToCursor$_to_cursor$arity$2(value,state);
} else
{var x__5489__auto__ = (((value == null))?null:value);return (function (){var or__4868__auto__ = (om.core._to_cursor[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core._to_cursor["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IToCursor.-to-cursor",value);
}
}
})().call(null,value,state);
}
});
var _to_cursor__3 = (function (value,state,path){if((function (){var and__4856__auto__ = value;if(and__4856__auto__)
{return value.om$core$IToCursor$_to_cursor$arity$3;
} else
{return and__4856__auto__;
}
})())
{return value.om$core$IToCursor$_to_cursor$arity$3(value,state,path);
} else
{var x__5489__auto__ = (((value == null))?null:value);return (function (){var or__4868__auto__ = (om.core._to_cursor[goog.typeOf(x__5489__auto__)]);if(or__4868__auto__)
{return or__4868__auto__;
} else
{var or__4868__auto____$1 = (om.core._to_cursor["_"]);if(or__4868__auto____$1)
{return or__4868__auto____$1;
} else
{throw cljs.core.missing_protocol.call(null,"IToCursor.-to-cursor",value);
}
}
})().call(null,value,state,path);
}
});
_to_cursor = function(value,state,path){
switch(arguments.length){
case 2:
return _to_cursor__2.call(this,value,state);
case 3:
return _to_cursor__3.call(this,value,state,path);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
_to_cursor.cljs$core$IFn$_invoke$arity$2 = _to_cursor__2;
_to_cursor.cljs$core$IFn$_invoke$arity$3 = _to_cursor__3;
return _to_cursor;
})()
;
om.core.children = (function children(node){var c = node.props.children;if(cljs.core.fn_QMARK_.call(null,c))
{return node.props.children = (function (){var _STAR_read_enabled_STAR_9269 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return c.call(null,node);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9269;
}})();
} else
{return c;
}
});
/**
* Given an owning Pure node return the Om props. Analogous to React
* component props.
*/
om.core.get_props = (function get_props(x){var ret = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"value","value",1125876963),(x.props["__om_cursor"]["value"])], null);var temp__4090__auto__ = (x.props["__om_index"]);if(cljs.core.truth_(temp__4090__auto__))
{var idx = temp__4090__auto__;return cljs.core.assoc.call(null,ret,new cljs.core.Keyword(null,"index","index",1114250308),idx);
} else
{return ret;
}
});
om.core.merge_pending_state = (function merge_pending_state(owner){var state = owner.state;var temp__4092__auto__ = (state["__om_pending_state"]);if(cljs.core.truth_(temp__4092__auto__))
{var pending_state = temp__4092__auto__;var G__9271 = state;(G__9271["__om_prev_state"] = (state["__om_state"]));
(G__9271["__om_state"] = pending_state);
(G__9271["__om_pending_state"] = null);
return G__9271;
} else
{return null;
}
});
om.core.Pure = React.createClass({"render": (function (){var this$ = this;var _STAR_read_enabled_STAR_9286 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return om.core.render.call(null,om.core.children.call(null,this$));
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9286;
}}), "componentDidUpdate": (function (prev_props,prev_state,root_node){var this$ = this;var c = om.core.children.call(null,this$);if((function (){var G__9284 = c;if(G__9284)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9284.om$core$IDidUpdate$;
}
})()))
{return true;
} else
{if((!G__9284.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IDidUpdate,G__9284);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IDidUpdate,G__9284);
}
})())
{var _STAR_read_enabled_STAR_9285_9287 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
om.core.did_update.call(null,c,om.core.get_props.call(null,{"props": prev_props}),(this$.state["__om_prev_state"]),root_node);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9285_9287;
}} else
{}
return (this$.state["__om_prev_state"] = null);
}), "componentWillUpdate": (function (next_props,next_state){var this$ = this;var c_9288 = om.core.children.call(null,this$);if((function (){var G__9282 = c_9288;if(G__9282)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9282.om$core$IWillUpdate$;
}
})()))
{return true;
} else
{if((!G__9282.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillUpdate,G__9282);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillUpdate,G__9282);
}
})())
{var _STAR_read_enabled_STAR_9283_9289 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
om.core.will_update.call(null,c_9288,om.core.get_props.call(null,{"props": next_props}),(this$.state["__om_pending_state"]));
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9283_9289;
}} else
{}
return om.core.merge_pending_state.call(null,this$);
}), "componentWillUnmount": (function (){var this$ = this;var c = om.core.children.call(null,this$);if((function (){var G__9280 = c;if(G__9280)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9280.om$core$IWillUnmount$;
}
})()))
{return true;
} else
{if((!G__9280.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillUnmount,G__9280);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillUnmount,G__9280);
}
})())
{var _STAR_read_enabled_STAR_9281 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return om.core.will_unmount.call(null,c);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9281;
}} else
{return null;
}
}), "componentDidMount": (function (node){var this$ = this;var c = om.core.children.call(null,this$);if((function (){var G__9278 = c;if(G__9278)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9278.om$core$IDidMount$;
}
})()))
{return true;
} else
{if((!G__9278.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IDidMount,G__9278);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IDidMount,G__9278);
}
})())
{var _STAR_read_enabled_STAR_9279 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return om.core.did_mount.call(null,c,node);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9279;
}} else
{return null;
}
}), "componentWillMount": (function (){var this$ = this;var c_9290 = om.core.children.call(null,this$);if((function (){var G__9276 = c_9290;if(G__9276)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9276.om$core$IWillMount$;
}
})()))
{return true;
} else
{if((!G__9276.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillMount,G__9276);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IWillMount,G__9276);
}
})())
{var _STAR_read_enabled_STAR_9277_9291 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
om.core.will_mount.call(null,c_9290);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9277_9291;
}} else
{}
return om.core.merge_pending_state.call(null,this$);
}), "shouldComponentUpdate": (function (next_props,next_state){var this$ = this;var _STAR_read_enabled_STAR_9274 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var props = this$.props;var c = om.core.children.call(null,this$);if((function (){var G__9275 = c;if(G__9275)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9275.om$core$IShouldUpdate$;
}
})()))
{return true;
} else
{if((!G__9275.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IShouldUpdate,G__9275);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IShouldUpdate,G__9275);
}
})())
{return om.core.should_update.call(null,c,om.core.get_props.call(null,{"props": next_props}),(this$.state["__om_pending_state"]));
} else
{if(!((om.core._value.call(null,(props["__om_cursor"])) === om.core._value.call(null,(next_props["__om_cursor"])))))
{return true;
} else
{if(!(((this$.state["__om_pending_state"]) == null)))
{return true;
} else
{if(!(((props["__om_index"]) === (next_props["__om_index"]))))
{return true;
} else
{if(new cljs.core.Keyword(null,"else","else",1017020587))
{return false;
} else
{return null;
}
}
}
}
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9274;
}}), "getInitialState": (function (){var this$ = this;var c = om.core.children.call(null,this$);return {"__om_state": cljs.core.merge.call(null,cljs.core.PersistentArrayMap.EMPTY,(((function (){var G__9272 = c;if(G__9272)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9272.om$core$IInitState$;
}
})()))
{return true;
} else
{if((!G__9272.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IInitState,G__9272);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IInitState,G__9272);
}
})())?(function (){var _STAR_read_enabled_STAR_9273 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return om.core.init_state.call(null,c);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9273;
}})():null))};
})});
om.core.path = (function path(cursor){return om.core._path.call(null,cursor);
});
om.core.value = (function value(cursor){if(om.core._STAR_read_enabled_STAR_)
{return om.core._value.call(null,cursor);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.cursor_QMARK_ = (function cursor_QMARK_(x){var G__9293 = x;if(G__9293)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9293.om$core$ICursor$;
}
})()))
{return true;
} else
{if((!G__9293.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.ICursor,G__9293);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.ICursor,G__9293);
}
});

/**
* @constructor
*/
om.core.MapCursor = (function (value,state,path){
this.value = value;
this.state = state;
this.path = path;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 2157971210;
})
om.core.MapCursor.cljs$lang$type = true;
om.core.MapCursor.cljs$lang$ctorStr = "om.core/MapCursor";
om.core.MapCursor.cljs$lang$ctorPrWriter = (function (this__5430__auto__,writer__5431__auto__,opt__5432__auto__){return cljs.core._write.call(null,writer__5431__auto__,"om.core/MapCursor");
});
om.core.MapCursor.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this$,k){var self__ = this;
var this$__$1 = this;return cljs.core._lookup.call(null,this$__$1,k,null);
});
om.core.MapCursor.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (_,k,not_found){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{var v = cljs.core._lookup.call(null,self__.value,k,not_found);if(!(cljs.core._EQ_.call(null,v,not_found)))
{return om.core.to_cursor.call(null,v,self__.state,cljs.core.conj.call(null,self__.path,k));
} else
{return not_found;
}
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (_,k){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._contains_key_QMARK_.call(null,self__.value,k);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (_,k,v){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return (new om.core.MapCursor(cljs.core._assoc.call(null,self__.value,k,v),self__.state,self__.path));
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.om$core$ICursor$ = true;
om.core.MapCursor.prototype.om$core$ICursor$_value$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.value;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.om$core$ICursor$_path$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.path;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.om$core$ICursor$_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.state;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (_,writer,opts){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._pr_writer.call(null,self__.value,writer,opts);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$ICollection$_conj$arity$2 = (function (_,o){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return (new om.core.MapCursor(cljs.core._conj.call(null,self__.value,o),self__.state,self__.path));
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core.map.call(null,(function (p__9294){var vec__9295 = p__9294;var k = cljs.core.nth.call(null,vec__9295,0,null);var v = cljs.core.nth.call(null,vec__9295,1,null);return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [k,om.core.to_cursor.call(null,v,self__.state,cljs.core.conj.call(null,self__.path,k))], null);
}),self__.value);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$ICounted$_count$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._count.call(null,self__.value);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (_,other){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{if(om.core.cursor_QMARK_.call(null,other))
{return (cljs.core._EQ_.call(null,self__.value,om.core._value.call(null,other))) && (cljs.core._EQ_.call(null,self__.state,om.core._state.call(null,other))) && (cljs.core._EQ_.call(null,self__.path,om.core._path.call(null,other)));
} else
{return cljs.core._EQ_.call(null,self__.value,other);
}
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.MapCursor.prototype.cljs$core$ICloneable$ = true;
om.core.MapCursor.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return (new om.core.MapCursor(self__.value,self__.state,self__.path));
});
om.core.MapCursor.prototype.cljs$core$IMap$_dissoc$arity$2 = (function (_,k){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return (new om.core.MapCursor(cljs.core._dissoc.call(null,self__.value,k),self__.state,self__.path));
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.__GT_MapCursor = (function __GT_MapCursor(value,state,path){return (new om.core.MapCursor(value,state,path));
});

/**
* @constructor
*/
om.core.IndexedCursor = (function (value,state,path){
this.value = value;
this.state = state;
this.path = path;
this.cljs$lang$protocol_mask$partition1$ = 0;
this.cljs$lang$protocol_mask$partition0$ = 2174755610;
})
om.core.IndexedCursor.cljs$lang$type = true;
om.core.IndexedCursor.cljs$lang$ctorStr = "om.core/IndexedCursor";
om.core.IndexedCursor.cljs$lang$ctorPrWriter = (function (this__5430__auto__,writer__5431__auto__,opt__5432__auto__){return cljs.core._write.call(null,writer__5431__auto__,"om.core/IndexedCursor");
});
om.core.IndexedCursor.prototype.cljs$core$ILookup$_lookup$arity$2 = (function (this$,n){var self__ = this;
var this$__$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._nth.call(null,this$__$1,n,null);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$ILookup$_lookup$arity$3 = (function (this$,n,not_found){var self__ = this;
var this$__$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._nth.call(null,this$__$1,n,not_found);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IAssociative$_contains_key_QMARK_$arity$2 = (function (_,k){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._contains_key_QMARK_.call(null,self__.value,k);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IAssociative$_assoc$arity$3 = (function (_,n,v){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return om.core.to_cursor.call(null,cljs.core._assoc_n.call(null,self__.value,n,v),self__.state,self__.path);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.om$core$ICursor$ = true;
om.core.IndexedCursor.prototype.om$core$ICursor$_value$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.value;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.om$core$ICursor$_path$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.path;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.om$core$ICursor$_state$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return self__.state;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IPrintWithWriter$_pr_writer$arity$3 = (function (_,writer,opts){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._pr_writer.call(null,self__.value,writer,opts);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$ICollection$_conj$arity$2 = (function (_,o){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return (new om.core.IndexedCursor(cljs.core._conj.call(null,self__.value,o),self__.state,self__.path));
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$ISeqable$_seq$arity$1 = (function (this$){var self__ = this;
var this$__$1 = this;if(om.core._STAR_read_enabled_STAR_)
{if((cljs.core.count.call(null,self__.value) > 0))
{return cljs.core.map.call(null,(function (v,i){return om.core.to_cursor.call(null,v,self__.state,cljs.core.conj.call(null,self__.path,i));
}),self__.value,cljs.core.range.call(null));
} else
{return null;
}
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$ICounted$_count$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return cljs.core._count.call(null,self__.value);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IStack$_peek$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return om.core.to_cursor.call(null,cljs.core._peek.call(null,self__.value),self__.state,self__.path);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IStack$_pop$arity$1 = (function (_){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return om.core.to_cursor.call(null,cljs.core._pop.call(null,self__.value),self__.state,self__.path);
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IEquiv$_equiv$arity$2 = (function (_,other){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{if(om.core.cursor_QMARK_.call(null,other))
{return (cljs.core._EQ_.call(null,self__.value,om.core._value.call(null,other))) && (cljs.core._EQ_.call(null,self__.state,om.core._state.call(null,other))) && (cljs.core._EQ_.call(null,self__.path,om.core._path.call(null,other)));
} else
{return cljs.core._EQ_.call(null,self__.value,other);
}
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$ICloneable$ = true;
om.core.IndexedCursor.prototype.cljs$core$ICloneable$_clone$arity$1 = (function (_){var self__ = this;
var ___$1 = this;return (new om.core.IndexedCursor(self__.value,self__.state,self__.path));
});
om.core.IndexedCursor.prototype.cljs$core$IIndexed$_nth$arity$2 = (function (_,n){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return om.core.to_cursor.call(null,cljs.core._nth.call(null,self__.value,n),self__.state,cljs.core.conj.call(null,self__.path,n));
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.IndexedCursor.prototype.cljs$core$IIndexed$_nth$arity$3 = (function (_,n,not_found){var self__ = this;
var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{if((n < cljs.core._count.call(null,self__.value)))
{return om.core.to_cursor.call(null,cljs.core._nth.call(null,self__.value,n),self__.state,cljs.core.conj.call(null,self__.path,n));
} else
{return not_found;
}
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
om.core.__GT_IndexedCursor = (function __GT_IndexedCursor(value,state,path){return (new om.core.IndexedCursor(value,state,path));
});
om.core.to_cursor_STAR_ = (function to_cursor_STAR_(val,state,path){var x9297 = cljs.core.clone.call(null,val);x9297.om$core$ICursor$ = true;
x9297.om$core$ICursor$_value$arity$1 = (function (_){var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return val;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
x9297.om$core$ICursor$_state$arity$1 = (function (_){var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return state;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
x9297.om$core$ICursor$_path$arity$1 = (function (_){var ___$1 = this;if(om.core._STAR_read_enabled_STAR_)
{return path;
} else
{throw (new Error([cljs.core.str("Cannot manipulate cursor outside of render phase, only "),cljs.core.str("om.core/transact!, om.core/update!, and om.core/read operations allowed")].join('')));
}
});
return x9297;
});
om.core.to_cursor = (function() {
var to_cursor = null;
var to_cursor__1 = (function (val){return to_cursor.call(null,val,null,cljs.core.PersistentVector.EMPTY);
});
var to_cursor__2 = (function (val,state){return to_cursor.call(null,val,state,cljs.core.PersistentVector.EMPTY);
});
var to_cursor__3 = (function (val,state,path){if(om.core.cursor_QMARK_.call(null,val))
{return val;
} else
{if((function (){var G__9300 = val;if(G__9300)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9300.om$core$IToCursor$;
}
})()))
{return true;
} else
{if((!G__9300.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IToCursor,G__9300);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,om.core.IToCursor,G__9300);
}
})())
{return om.core._to_cursor.call(null,val,state,path);
} else
{if(cljs.core.indexed_QMARK_.call(null,val))
{return (new om.core.IndexedCursor(val,state,path));
} else
{if(cljs.core.map_QMARK_.call(null,val))
{return (new om.core.MapCursor(val,state,path));
} else
{if((function (){var G__9301 = val;if(G__9301)
{var bit__5512__auto__ = null;if(cljs.core.truth_((function (){var or__4868__auto__ = bit__5512__auto__;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return G__9301.cljs$core$ICloneable$;
}
})()))
{return true;
} else
{if((!G__9301.cljs$lang$protocol_mask$partition$))
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.ICloneable,G__9301);
} else
{return false;
}
}
} else
{return cljs.core.native_satisfies_QMARK_.call(null,cljs.core.ICloneable,G__9301);
}
})())
{return om.core.to_cursor_STAR_.call(null,val,state,path);
} else
{if(new cljs.core.Keyword(null,"else","else",1017020587))
{return val;
} else
{return null;
}
}
}
}
}
}
});
to_cursor = function(val,state,path){
switch(arguments.length){
case 1:
return to_cursor__1.call(this,val);
case 2:
return to_cursor__2.call(this,val,state);
case 3:
return to_cursor__3.call(this,val,state,path);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
to_cursor.cljs$core$IFn$_invoke$arity$1 = to_cursor__1;
to_cursor.cljs$core$IFn$_invoke$arity$2 = to_cursor__2;
to_cursor.cljs$core$IFn$_invoke$arity$3 = to_cursor__3;
return to_cursor;
})()
;
om.core.refresh_queued = false;
/**
* Takes an immutable value or value wrapped in an atom, an initial
* function f, and a DOM target. Installs an Om/React render loop. f
* must return an instance that at a minimum implements IRender (it
* may implement other React life cycle protocols). f must take
* two arguments, the root cursor and the owning pure node. A
* cursor is just the original data wrapped in an ICursor instance
* which maintains path information.
* 
* Example:
* 
* (root {:message :hello}
* (fn [data]
* ...)
* js/document.body)
*/
om.core.root = (function root(value,f,target){var state = (((value instanceof cljs.core.Atom))?value:cljs.core.atom.call(null,value));var rootf = ((function (state){
return (function (){om.core.refresh_queued = false;
var value__$1 = cljs.core.deref.call(null,state);var cursor = om.core.to_cursor.call(null,value__$1,state);return React.renderComponent((new om.core.Pure({"__om_cursor": cursor},((function (value__$1,cursor,state){
return (function (this$){var _STAR_read_enabled_STAR_9303 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return f.call(null,cursor,this$);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9303;
}});})(value__$1,cursor,state))
)),target);
});})(state))
;cljs.core.add_watch.call(null,state,new cljs.core.Keyword("om.core","root","om.core/root",2668054562),(function (_,___$1,___$2,___$3){if(cljs.core.truth_(om.core.refresh_queued))
{return null;
} else
{om.core.refresh_queued = true;
if(typeof requestAnimationFrame !== 'undefined')
{return requestAnimationFrame(rootf);
} else
{return setTimeout(rootf,16);
}
}
}));
return rootf.call(null);
});
om.core.om_key = (function om_key(f){return [cljs.core.str("_om_"),cljs.core.str(goog.getUid(f))].join('');
});
/**
* Builds a Om component. Takes an IRender instance returning function
* f, a cursor, and an optional third argument which may be a map of
* build specifications.
* 
* f - is a function of 2 or 3 arguments. The first argument will
* be the cursor and the second argument will be the owning pure node.
* If a map of options m is passed in this will be the third
* argument. f must return at a minimum an IRender instance, this
* instance may implement other React life cycle protocols.
* 
* cursor - an ICursor instance
* 
* m - a map the following keys are allowed:
* 
* :key       - a keyword that should be used to look up the key used by
* React itself when rendering sequential things.
* :react-key - an explicit react key
* :fn        - a function to apply to the data at the relative path before
* invoking f.
* :opts      - a map of options to pass to the component.
* 
* Example:
* 
* (build list-of-gadgets cursor
* {:opts {:event-chan ...
* :narble ...}})
* 
*/
om.core.build = (function() {
var build = null;
var build__2 = (function (f,cursor){return build.call(null,f,cursor,null);
});
var build__3 = (function (f,cursor,m){if((m == null))
{return (new om.core.Pure({"key": om.core.om_key.call(null,f), "__om_cursor": cursor},(function (this$){if(om.core.cursor_QMARK_.call(null,cursor))
{var _STAR_read_enabled_STAR_9308 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return f.call(null,cursor,this$);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9308;
}} else
{throw (new Error([cljs.core.str("Cannot build Om component from non-cursor "),cljs.core.str(cursor)].join('')));
}
})));
} else
{if(new cljs.core.Keyword(null,"else","else",1017020587))
{var map__9309 = m;var map__9309__$1 = ((cljs.core.seq_QMARK_.call(null,map__9309))?cljs.core.apply.call(null,cljs.core.hash_map,map__9309):map__9309);var opts = cljs.core.get.call(null,map__9309__$1,new cljs.core.Keyword(null,"opts","opts",1017322386));var key = cljs.core.get.call(null,map__9309__$1,new cljs.core.Keyword(null,"key","key",1014010321));var dataf = cljs.core.get.call(null,m,new cljs.core.Keyword(null,"fn","fn",1013907514));var cursor_SINGLEQUOTE_ = ((!((dataf == null)))?dataf.call(null,cursor):cursor);var rkey = ((!((key == null)))?cljs.core.get.call(null,cursor_SINGLEQUOTE_,key):cljs.core.get.call(null,m,new cljs.core.Keyword(null,"react-key","react-key",4184082563)));return (new om.core.Pure({"key": (function (){var or__4868__auto__ = rkey;if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return om.core.om_key.call(null,f);
}
})(), "__om_index": new cljs.core.Keyword("om.core","index","om.core/index",3166146514).cljs$core$IFn$_invoke$arity$1(m), "__om_cursor": cursor_SINGLEQUOTE_},(((opts == null))?(function (this$){if(om.core.cursor_QMARK_.call(null,cursor_SINGLEQUOTE_))
{var _STAR_read_enabled_STAR_9310 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return f.call(null,cursor_SINGLEQUOTE_,this$);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9310;
}} else
{throw (new Error([cljs.core.str("Cannot build Om component from non-cursor "),cljs.core.str(cursor_SINGLEQUOTE_)].join('')));
}
}):(function (this$){if(om.core.cursor_QMARK_.call(null,cursor_SINGLEQUOTE_))
{var _STAR_read_enabled_STAR_9311 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
return f.call(null,cursor_SINGLEQUOTE_,this$,opts);
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9311;
}} else
{throw (new Error([cljs.core.str("Cannot build Om component from non-cursor "),cljs.core.str(cursor_SINGLEQUOTE_)].join('')));
}
}))));
} else
{return null;
}
}
});
build = function(f,cursor,m){
switch(arguments.length){
case 2:
return build__2.call(this,f,cursor);
case 3:
return build__3.call(this,f,cursor,m);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
build.cljs$core$IFn$_invoke$arity$2 = build__2;
build.cljs$core$IFn$_invoke$arity$3 = build__3;
return build;
})()
;
/**
* Build a sequence of components. f is the component constructor
* function, xs a sequence of cursors, and m a map of options the
* same as provided to om.core/build.
*/
om.core.build_all = (function() {
var build_all = null;
var build_all__2 = (function (f,xs){return build_all.call(null,f,xs,null);
});
var build_all__3 = (function (f,xs,m){return cljs.core.into_array.call(null,cljs.core.map.call(null,(function (x,i){return om.core.build.call(null,f,x,cljs.core.assoc.call(null,m,new cljs.core.Keyword("om.core","index","om.core/index",3166146514),i));
}),xs,cljs.core.range.call(null)));
});
build_all = function(f,xs,m){
switch(arguments.length){
case 2:
return build_all__2.call(this,f,xs);
case 3:
return build_all__3.call(this,f,xs,m);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
build_all.cljs$core$IFn$_invoke$arity$2 = build_all__2;
build_all.cljs$core$IFn$_invoke$arity$3 = build_all__3;
return build_all;
})()
;
/**
* Given a cursor, an optional list of keys ks, mutate the tree at the
* path specified by the cursor + the optional keys by applying f to the
* specified value in the tree. An Om re-render will be triggered.
* @param {...*} var_args
*/
om.core.transact_BANG_ = (function() {
var transact_BANG_ = null;
var transact_BANG___2 = (function (cursor,f){var _STAR_read_enabled_STAR_9319 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var state = om.core._state.call(null,cursor);var path = om.core._path.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path))
{return cljs.core.swap_BANG_.call(null,state,f);
} else
{return cljs.core.swap_BANG_.call(null,state,cljs.core.update_in,path,f);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9319;
}});
var transact_BANG___3 = (function (cursor,korks,f){var _STAR_read_enabled_STAR_9320 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7986__auto__ = om.core._path.call(null,cursor);var state__7987__auto__ = om.core._state.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.conj.call(null,path__7986__auto__,korks),f);
} else
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.into.call(null,path__7986__auto__,korks),f);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9320;
}});
var transact_BANG___4 = (function (cursor,korks,f,a){var _STAR_read_enabled_STAR_9321 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7986__auto__ = om.core._path.call(null,cursor);var state__7987__auto__ = om.core._state.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.conj.call(null,path__7986__auto__,korks),f,a);
} else
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.into.call(null,path__7986__auto__,korks),f,a);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9321;
}});
var transact_BANG___5 = (function (cursor,korks,f,a,b){var _STAR_read_enabled_STAR_9322 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7986__auto__ = om.core._path.call(null,cursor);var state__7987__auto__ = om.core._state.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.conj.call(null,path__7986__auto__,korks),f,a,b);
} else
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.into.call(null,path__7986__auto__,korks),f,a,b);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9322;
}});
var transact_BANG___6 = (function (cursor,korks,f,a,b,c){var _STAR_read_enabled_STAR_9323 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7986__auto__ = om.core._path.call(null,cursor);var state__7987__auto__ = om.core._state.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.conj.call(null,path__7986__auto__,korks),f,a,b,c);
} else
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.into.call(null,path__7986__auto__,korks),f,a,b,c);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9323;
}});
var transact_BANG___7 = (function (cursor,korks,f,a,b,c,d){var _STAR_read_enabled_STAR_9324 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7986__auto__ = om.core._path.call(null,cursor);var state__7987__auto__ = om.core._state.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.conj.call(null,path__7986__auto__,korks),f,a,b,c,d);
} else
{return cljs.core.swap_BANG_.call(null,state__7987__auto__,cljs.core.update_in,cljs.core.into.call(null,path__7986__auto__,korks),f,a,b,c,d);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9324;
}});
var transact_BANG___8 = (function() { 
var G__9326__delegate = function (cursor,korks,f,a,b,c,d,args){var _STAR_read_enabled_STAR_9325 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var state = om.core._state.call(null,cursor);var path = om.core._path.call(null,cursor);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.apply.call(null,cljs.core.swap_BANG_,state,cljs.core.update_in,cljs.core.conj.call(null,path,korks),f,a,b,c,d,args);
} else
{return cljs.core.apply.call(null,cljs.core.swap_BANG_,state,cljs.core.update_in,cljs.core.into.call(null,path,korks),f,a,b,c,d,args);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9325;
}};
var G__9326 = function (cursor,korks,f,a,b,c,d,var_args){
var args = null;if (arguments.length > 7) {
  args = cljs.core.array_seq(Array.prototype.slice.call(arguments, 7),0);} 
return G__9326__delegate.call(this,cursor,korks,f,a,b,c,d,args);};
G__9326.cljs$lang$maxFixedArity = 7;
G__9326.cljs$lang$applyTo = (function (arglist__9327){
var cursor = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var korks = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var f = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var a = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var b = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var c = cljs.core.first(arglist__9327);
arglist__9327 = cljs.core.next(arglist__9327);
var d = cljs.core.first(arglist__9327);
var args = cljs.core.rest(arglist__9327);
return G__9326__delegate(cursor,korks,f,a,b,c,d,args);
});
G__9326.cljs$core$IFn$_invoke$arity$variadic = G__9326__delegate;
return G__9326;
})()
;
transact_BANG_ = function(cursor,korks,f,a,b,c,d,var_args){
var args = var_args;
switch(arguments.length){
case 2:
return transact_BANG___2.call(this,cursor,korks);
case 3:
return transact_BANG___3.call(this,cursor,korks,f);
case 4:
return transact_BANG___4.call(this,cursor,korks,f,a);
case 5:
return transact_BANG___5.call(this,cursor,korks,f,a,b);
case 6:
return transact_BANG___6.call(this,cursor,korks,f,a,b,c);
case 7:
return transact_BANG___7.call(this,cursor,korks,f,a,b,c,d);
default:
return transact_BANG___8.cljs$core$IFn$_invoke$arity$variadic(cursor,korks,f,a,b,c,d, cljs.core.array_seq(arguments, 7));
}
throw(new Error('Invalid arity: ' + arguments.length));
};
transact_BANG_.cljs$lang$maxFixedArity = 7;
transact_BANG_.cljs$lang$applyTo = transact_BANG___8.cljs$lang$applyTo;
transact_BANG_.cljs$core$IFn$_invoke$arity$2 = transact_BANG___2;
transact_BANG_.cljs$core$IFn$_invoke$arity$3 = transact_BANG___3;
transact_BANG_.cljs$core$IFn$_invoke$arity$4 = transact_BANG___4;
transact_BANG_.cljs$core$IFn$_invoke$arity$5 = transact_BANG___5;
transact_BANG_.cljs$core$IFn$_invoke$arity$6 = transact_BANG___6;
transact_BANG_.cljs$core$IFn$_invoke$arity$7 = transact_BANG___7;
transact_BANG_.cljs$core$IFn$_invoke$arity$variadic = transact_BANG___8.cljs$core$IFn$_invoke$arity$variadic;
return transact_BANG_;
})()
;
/**
* Like transact! but no list of keys given. An Om re-render
* will be triggered.
* @param {...*} var_args
*/
om.core.update_BANG_ = (function() {
var update_BANG_ = null;
var update_BANG___2 = (function (cursor,f){var _STAR_read_enabled_STAR_9335 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7996__auto__ = om.core._path.call(null,cursor);var state__7997__auto__ = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path__7996__auto__))
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,(function (p1__7995__7998__auto__){return f.call(null,p1__7995__7998__auto__);
}));
} else
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,cljs.core.update_in,path__7996__auto__,f);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9335;
}});
var update_BANG___3 = (function (cursor,f,a){var _STAR_read_enabled_STAR_9336 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7996__auto__ = om.core._path.call(null,cursor);var state__7997__auto__ = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path__7996__auto__))
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,(function (p1__7995__7998__auto__){return f.call(null,p1__7995__7998__auto__,a);
}));
} else
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,cljs.core.update_in,path__7996__auto__,f,a);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9336;
}});
var update_BANG___4 = (function (cursor,f,a,b){var _STAR_read_enabled_STAR_9337 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7996__auto__ = om.core._path.call(null,cursor);var state__7997__auto__ = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path__7996__auto__))
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,(function (p1__7995__7998__auto__){return f.call(null,p1__7995__7998__auto__,a,b);
}));
} else
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,cljs.core.update_in,path__7996__auto__,f,a,b);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9337;
}});
var update_BANG___5 = (function (cursor,f,a,b,c){var _STAR_read_enabled_STAR_9338 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7996__auto__ = om.core._path.call(null,cursor);var state__7997__auto__ = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path__7996__auto__))
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,(function (p1__7995__7998__auto__){return f.call(null,p1__7995__7998__auto__,a,b,c);
}));
} else
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,cljs.core.update_in,path__7996__auto__,f,a,b,c);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9338;
}});
var update_BANG___6 = (function (cursor,f,a,b,c,d){var _STAR_read_enabled_STAR_9339 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path__7996__auto__ = om.core._path.call(null,cursor);var state__7997__auto__ = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path__7996__auto__))
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,(function (p1__7995__7998__auto__){return f.call(null,p1__7995__7998__auto__,a,b,c,d);
}));
} else
{return cljs.core.swap_BANG_.call(null,state__7997__auto__,cljs.core.update_in,path__7996__auto__,f,a,b,c,d);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9339;
}});
var update_BANG___7 = (function() { 
var G__9341__delegate = function (cursor,f,a,b,c,d,args){var _STAR_read_enabled_STAR_9340 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path = om.core._path.call(null,cursor);var state = om.core._state.call(null,cursor);if(cljs.core.empty_QMARK_.call(null,path))
{return cljs.core.swap_BANG_.call(null,state,(function (p1__9328_SHARP_){return cljs.core.apply.call(null,f,p1__9328_SHARP_,a,b,c,d,args);
}));
} else
{return cljs.core.apply.call(null,cljs.core.swap_BANG_,state,cljs.core.update_in,path,f,a,b,c,d,args);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9340;
}};
var G__9341 = function (cursor,f,a,b,c,d,var_args){
var args = null;if (arguments.length > 6) {
  args = cljs.core.array_seq(Array.prototype.slice.call(arguments, 6),0);} 
return G__9341__delegate.call(this,cursor,f,a,b,c,d,args);};
G__9341.cljs$lang$maxFixedArity = 6;
G__9341.cljs$lang$applyTo = (function (arglist__9342){
var cursor = cljs.core.first(arglist__9342);
arglist__9342 = cljs.core.next(arglist__9342);
var f = cljs.core.first(arglist__9342);
arglist__9342 = cljs.core.next(arglist__9342);
var a = cljs.core.first(arglist__9342);
arglist__9342 = cljs.core.next(arglist__9342);
var b = cljs.core.first(arglist__9342);
arglist__9342 = cljs.core.next(arglist__9342);
var c = cljs.core.first(arglist__9342);
arglist__9342 = cljs.core.next(arglist__9342);
var d = cljs.core.first(arglist__9342);
var args = cljs.core.rest(arglist__9342);
return G__9341__delegate(cursor,f,a,b,c,d,args);
});
G__9341.cljs$core$IFn$_invoke$arity$variadic = G__9341__delegate;
return G__9341;
})()
;
update_BANG_ = function(cursor,f,a,b,c,d,var_args){
var args = var_args;
switch(arguments.length){
case 2:
return update_BANG___2.call(this,cursor,f);
case 3:
return update_BANG___3.call(this,cursor,f,a);
case 4:
return update_BANG___4.call(this,cursor,f,a,b);
case 5:
return update_BANG___5.call(this,cursor,f,a,b,c);
case 6:
return update_BANG___6.call(this,cursor,f,a,b,c,d);
default:
return update_BANG___7.cljs$core$IFn$_invoke$arity$variadic(cursor,f,a,b,c,d, cljs.core.array_seq(arguments, 6));
}
throw(new Error('Invalid arity: ' + arguments.length));
};
update_BANG_.cljs$lang$maxFixedArity = 6;
update_BANG_.cljs$lang$applyTo = update_BANG___7.cljs$lang$applyTo;
update_BANG_.cljs$core$IFn$_invoke$arity$2 = update_BANG___2;
update_BANG_.cljs$core$IFn$_invoke$arity$3 = update_BANG___3;
update_BANG_.cljs$core$IFn$_invoke$arity$4 = update_BANG___4;
update_BANG_.cljs$core$IFn$_invoke$arity$5 = update_BANG___5;
update_BANG_.cljs$core$IFn$_invoke$arity$6 = update_BANG___6;
update_BANG_.cljs$core$IFn$_invoke$arity$variadic = update_BANG___7.cljs$core$IFn$_invoke$arity$variadic;
return update_BANG_;
})()
;
/**
* Given a cursor and a function f, read its current value. f will be
* passed a cursor which can only be read in the scope of f. Can take
* an optional sequence of keys ks. Used for interacting with cursors
* outside of render phase.
*/
om.core.read = (function() {
var read = null;
var read__2 = (function (cursor,f){return read.call(null,cursor,cljs.core.List.EMPTY,f);
});
var read__3 = (function (cursor,korks,f){var _STAR_read_enabled_STAR_9344 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var path = ((!(cljs.core.sequential_QMARK_.call(null,korks)))?cljs.core.conj.call(null,om.core._path.call(null,cursor),korks):cljs.core.into.call(null,om.core._path.call(null,cursor),korks));var state = om.core._state.call(null,cursor);var value = cljs.core.deref.call(null,state);if(cljs.core.empty_QMARK_.call(null,path))
{return f.call(null,om.core.to_cursor.call(null,value,state,cljs.core.PersistentVector.EMPTY));
} else
{return f.call(null,om.core.to_cursor.call(null,cljs.core.get_in.call(null,value,path),state,path));
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9344;
}});
read = function(cursor,korks,f){
switch(arguments.length){
case 2:
return read__2.call(this,cursor,korks);
case 3:
return read__3.call(this,cursor,korks,f);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
read.cljs$core$IFn$_invoke$arity$2 = read__2;
read.cljs$core$IFn$_invoke$arity$3 = read__3;
return read;
})()
;
/**
* EXPERIMENTAL: Given a cursor, get value from the root at the path
* specified by a sequential list of keys ks.
*/
om.core.join = (function join(cursor,korks){var _STAR_read_enabled_STAR_9346 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
var state = om.core._state.call(null,cursor);var value = cljs.core.deref.call(null,state);if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return om.core.to_cursor.call(null,cljs.core.get.call(null,value,korks),state,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [korks], null));
} else
{return om.core.to_cursor.call(null,cljs.core.get_in.call(null,value,korks),state,((cljs.core.vector_QMARK_.call(null,korks))?korks:cljs.core.into.call(null,cljs.core.PersistentVector.EMPTY,korks)));
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9346;
}});
/**
* A helper function to get at React refs. Given a owning pure node
* extract the ref specified by name.
*/
om.core.get_node = (function get_node(owner,name){var temp__4092__auto__ = owner.refs;if(cljs.core.truth_(temp__4092__auto__))
{var refs = temp__4092__auto__;return (refs[name]).getDOMNode();
} else
{return null;
}
});
/**
* Takes a pure owning component, a sequential list of keys and value and
* sets the state of the component. Conceptually analagous to React
* setState. Will schedule an Om re-render.
*/
om.core.set_state_BANG_ = (function set_state_BANG_(owner,korks,v){var props = owner.props;var state = owner.state;var cursor = (props["__om_cursor"]);var path = om.core._path.call(null,cursor);var pstate = (function (){var or__4868__auto__ = (state["__om_pending_state"]);if(cljs.core.truth_(or__4868__auto__))
{return or__4868__auto__;
} else
{return (state["__om_state"]);
}
})();if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{(state["__om_pending_state"] = cljs.core.assoc.call(null,pstate,korks,v));
} else
{(state["__om_pending_state"] = cljs.core.assoc_in.call(null,pstate,korks,v));
}
var _STAR_read_enabled_STAR_9348 = om.core._STAR_read_enabled_STAR_;try{om.core._STAR_read_enabled_STAR_ = true;
if(cljs.core.empty_QMARK_.call(null,path))
{return cljs.core.swap_BANG_.call(null,om.core._state.call(null,cursor),cljs.core.clone);
} else
{return cljs.core.swap_BANG_.call(null,om.core._state.call(null,cursor),cljs.core.update_in,path,cljs.core.clone);
}
}finally {om.core._STAR_read_enabled_STAR_ = _STAR_read_enabled_STAR_9348;
}});
/**
* Takes a pure owning component and sequential list of keys and returns
* a property if it exists. Will never return pending state values.
*/
om.core.get_state = (function() {
var get_state = null;
var get_state__1 = (function (owner){return (owner.state["__om_state"]);
});
var get_state__2 = (function (owner,korks){if(!(cljs.core.sequential_QMARK_.call(null,korks)))
{return cljs.core.get.call(null,(owner.state["__om_state"]),korks);
} else
{if(cljs.core.empty_QMARK_.call(null,korks))
{return get_state.call(null,owner);
} else
{if(new cljs.core.Keyword(null,"else","else",1017020587))
{return cljs.core.get_in.call(null,(owner.state["__om_state"]),korks);
} else
{return null;
}
}
}
});
get_state = function(owner,korks){
switch(arguments.length){
case 1:
return get_state__1.call(this,owner);
case 2:
return get_state__2.call(this,owner,korks);
}
throw(new Error('Invalid arity: ' + arguments.length));
};
get_state.cljs$core$IFn$_invoke$arity$1 = get_state__1;
get_state.cljs$core$IFn$_invoke$arity$2 = get_state__2;
return get_state;
})()
;
/**
* Convenience function for creating event handlers on cursors. Takes
* a function f which should receive the event as the first argument,
* the cursor as the second argument, and any number of optional
* arguments beyond that. Inside of f the cursor will be readable.
* @param {...*} var_args
*/
om.core.bind = (function() {
var bind = null;
var bind__2 = (function (f,cursor){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return f.call(null,e,cursor__$1);
}));
});
});
var bind__3 = (function (f,cursor,a){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return f.call(null,e,cursor__$1,a);
}));
});
});
var bind__4 = (function (f,cursor,a,b){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return f.call(null,e,cursor__$1,a,b);
}));
});
});
var bind__5 = (function (f,cursor,a,b,c){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return f.call(null,e,cursor__$1,a,b,c);
}));
});
});
var bind__6 = (function (f,cursor,a,b,c,d){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return f.call(null,e,cursor__$1,a,b,c,d);
}));
});
});
var bind__7 = (function() { 
var G__9349__delegate = function (f,cursor,a,b,c,d,args){return (function (e){return om.core.read.call(null,cursor,(function (cursor__$1){return cljs.core.apply.call(null,f,e,cursor__$1,a,b,c,d,args);
}));
});
};
var G__9349 = function (f,cursor,a,b,c,d,var_args){
var args = null;if (arguments.length > 6) {
  args = cljs.core.array_seq(Array.prototype.slice.call(arguments, 6),0);} 
return G__9349__delegate.call(this,f,cursor,a,b,c,d,args);};
G__9349.cljs$lang$maxFixedArity = 6;
G__9349.cljs$lang$applyTo = (function (arglist__9350){
var f = cljs.core.first(arglist__9350);
arglist__9350 = cljs.core.next(arglist__9350);
var cursor = cljs.core.first(arglist__9350);
arglist__9350 = cljs.core.next(arglist__9350);
var a = cljs.core.first(arglist__9350);
arglist__9350 = cljs.core.next(arglist__9350);
var b = cljs.core.first(arglist__9350);
arglist__9350 = cljs.core.next(arglist__9350);
var c = cljs.core.first(arglist__9350);
arglist__9350 = cljs.core.next(arglist__9350);
var d = cljs.core.first(arglist__9350);
var args = cljs.core.rest(arglist__9350);
return G__9349__delegate(f,cursor,a,b,c,d,args);
});
G__9349.cljs$core$IFn$_invoke$arity$variadic = G__9349__delegate;
return G__9349;
})()
;
bind = function(f,cursor,a,b,c,d,var_args){
var args = var_args;
switch(arguments.length){
case 2:
return bind__2.call(this,f,cursor);
case 3:
return bind__3.call(this,f,cursor,a);
case 4:
return bind__4.call(this,f,cursor,a,b);
case 5:
return bind__5.call(this,f,cursor,a,b,c);
case 6:
return bind__6.call(this,f,cursor,a,b,c,d);
default:
return bind__7.cljs$core$IFn$_invoke$arity$variadic(f,cursor,a,b,c,d, cljs.core.array_seq(arguments, 6));
}
throw(new Error('Invalid arity: ' + arguments.length));
};
bind.cljs$lang$maxFixedArity = 6;
bind.cljs$lang$applyTo = bind__7.cljs$lang$applyTo;
bind.cljs$core$IFn$_invoke$arity$2 = bind__2;
bind.cljs$core$IFn$_invoke$arity$3 = bind__3;
bind.cljs$core$IFn$_invoke$arity$4 = bind__4;
bind.cljs$core$IFn$_invoke$arity$5 = bind__5;
bind.cljs$core$IFn$_invoke$arity$6 = bind__6;
bind.cljs$core$IFn$_invoke$arity$variadic = bind__7.cljs$core$IFn$_invoke$arity$variadic;
return bind;
})()
;
