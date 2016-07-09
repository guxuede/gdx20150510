/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.guxuede.game.animation;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;
import com.guxuede.game.actor.AnimationEntity;

public class ActorMoveDirectiveAction  extends RelativeTemporalAction  {
	public int direction;
	public static final int DOWN=1,LEFT=2,RIGHT=3,UP=4;

	@Override
	protected void updateRelative(float percentDelta) {
		
	}

	@Override
	protected void begin() {
		super.begin();
		AnimationEntity actor = ((AnimationEntity)target);
		actor.isMoving = true;
        if(direction==DOWN || direction==UP || direction==LEFT || direction==RIGHT){
            actor.setDirection(direction);
        }else{
        	actor.isMoving = false;
        }
	}
	
	@Override
	protected void end() {
		super.end();
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}



}
