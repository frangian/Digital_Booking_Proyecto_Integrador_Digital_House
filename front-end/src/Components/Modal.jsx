import React from "react";

const Modal = ({ onClose, children }) => {
  return (
    <div className="modal-container">
      <div className="modal-content">
        <button className="close-btn" onClick={onClose}>
          X
        </button>
        {children}
      </div>
    </div>
  );
};

export default Modal;