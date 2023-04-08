import React, { useState } from 'react'

const useToggleState = (
    initialValue = false,
    ) => {
    const [state, setState] = useState(initialValue);

    const toggleState =()=> {
        setState((state) => !state);
    };

    return [state, toggleState];
};

export default useToggleState;